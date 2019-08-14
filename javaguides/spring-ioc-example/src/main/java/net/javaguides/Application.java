package net.javaguides;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        HelloWorld bean = (HelloWorld) ctx.getBean("helloWorld");
        bean.getMessage();
        ctx.close();
    }
}
