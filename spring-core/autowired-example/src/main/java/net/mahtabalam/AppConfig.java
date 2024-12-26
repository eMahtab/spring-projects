package net.mahtabalam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public GreetingService greetingService() {
        return new GreetingService();
    }

    @Bean
    public FieldInjectionExample fieldInjectionExample() {
        return new FieldInjectionExample();
    }

    @Bean
    public SetterInjectionExample setterInjectionExample() {
        return new SetterInjectionExample();
    }

    @Bean
    public ConstructorInjectionExample constructorInjectionExample() {
        return new ConstructorInjectionExample(greetingService());
    }
}