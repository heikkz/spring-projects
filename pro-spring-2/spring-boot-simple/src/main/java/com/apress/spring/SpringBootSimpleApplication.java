package com.apress.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class SpringBootSimpleApplication implements CommandLineRunner, ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootSimpleApplication.class);

    public static void main(String[] args) {


        SpringApplication.run(SpringBootSimpleApplication.class, args);

//        SpringApplication app = new SpringApplication(SpringBootSimpleApplication.class);
//        app.run(args);

//        new SpringApplicationBuilder()
//                .bannerMode(Banner.Mode.OFF)
//                .sources(SpringBootSimpleApplication.class)
////                .logStartupInfo(false)
////                .profiles("prod", "cloud")
//                .run(args);


//        new SpringApplicationBuilder(SpringBootSimpleApplication.class)
//                .web(WebApplicationType.NONE)
//                .listeners(new ApplicationListener<ApplicationEvent>() {
//                    @Override
//                    public void onApplicationEvent(ApplicationEvent event) {
//                        log.info("### > " + event.getClass().getCanonicalName());
//                    }
//                })
//                .run(args);


    }

    @Bean
    String info() {
        return "Just a simple String bean";
    }

    @Autowired
    String info;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("## > ApplicationRunner implementation...");
        log.info("Accessing the Info bean: " + info);
        args.getNonOptionArgs().forEach(log::info);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("## > CommandLineRunner Implementation...");
        log.info("Accessing the Info bean: " + info);
        for (String arg : args) {
            log.info(arg);
        }
    }
}

//@Component
//class MyComponent {
//
//    private static final Logger log = LoggerFactory.getLogger(MyComponent.class);
//
//    @Autowired
//    public MyComponent(ApplicationArguments args) {
//        boolean enable = args.containsOption("enable");
//        if (enable) {
//            log.info("## > You are enabled!");
//        }
//
//        List<String> _args = args.getNonOptionArgs();
//        if (!_args.isEmpty()) {
//            _args.forEach(log::info);
//        }
//    }
//}
