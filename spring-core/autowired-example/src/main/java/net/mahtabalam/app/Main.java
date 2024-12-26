package net.mahtabalam;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConstructorInjectionExample constructorExample = context.getBean(ConstructorInjectionExample.class);
        constructorExample.showGreeting();

        FieldInjectionExample fieldExample = context.getBean(FieldInjectionExample.class);
        fieldExample.showGreeting();

        SetterInjectionExample setterExample = context.getBean(SetterInjectionExample.class);
        setterExample.showGreeting();
    }
}