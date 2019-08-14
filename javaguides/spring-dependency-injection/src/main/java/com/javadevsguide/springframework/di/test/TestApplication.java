package com.javadevsguide.springframework.di.test;

import com.javadevsguide.springframework.di.config.AppConfig;
import com.javadevsguide.springframework.di.constructor.ConstructorBasedInjection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ConstructorBasedInjection bean = ctx.getBean(ConstructorBasedInjection.class);
        bean.processMsg("twitter message sending");
    }
}
