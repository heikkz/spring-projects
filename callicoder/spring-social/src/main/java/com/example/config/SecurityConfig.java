package com.example.config;

import com.example.security.CustomUserDetailsService;
import com.example.security.RestAuthenticationEntryPoint;
import com.example.security.TokenAuthenticationFilter;
import com.example.security.oauth2.CustomOAuth2UserService;
import com.example.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.example.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
OAuth2 Login Flow
- The OAuth2 login flow will be initiated by the frontend client by sending the user to the endpoint http://localhost:8080/oauth2/authorize/{provider}?redirect_uri=<redirect_uri_after_login>.
- The provider path parameter is one of google, facebook, or github. The redirect_uri is the URI to which the user will be redirected once the authentication with the OAuth2 provider is successful. This is different from the OAuth2 redirectUri.
- On receiving the authorization request, Spring Security’s OAuth2 client will redirect the user to the AuthorizationUrl of the supplied provider.
- All the state related to the authorization request is saved using the authorizationRequestRepository specified in the SecurityConfig.
- The user now allows/denies permission to your app on the provider’s page. If the user allows permission to the app, the provider will redirect the user to the callback url http://localhost:8080/oauth2/callback/{provider} with an authorization code. If the user denies the permission, he will be redirected to the same callbackUrl but with an error.
- If the OAuth2 callback results in an error, Spring security will invoke the oAuth2AuthenticationFailureHandler specified in the above SecurityConfig.
- If the OAuth2 callback is successful and it contains the authorization code, Spring Security will exchange the authorization_code for an access_token and invoke the customOAuth2UserService specified in the above SecurityConfig.
- The customOAuth2UserService retrieves the details of the authenticated user and creates a new entry in the database or updates the existing entry with the same email.
- Finally, the oAuth2AuthenticationSuccessHandler is invoked. It creates a JWT authentication token for the user and sends the user to the redirect_uri along with the JWT token in a query string.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

     /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
     @Bean
    HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
         return new HttpCookieOAuth2AuthorizationRequestRepository();
     }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                            "/error",
                            "/favicon.ico",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js").permitAll()
                    .antMatchers("/auth/**", "/oauth2/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                    .and()
                .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                    .and()
                .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                    .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
