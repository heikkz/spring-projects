package net.javaguides.spring.dependson;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        FirstBean bean = ctx.getBean(FirstBean.class);
        bean.populateBeans();
    }
}
