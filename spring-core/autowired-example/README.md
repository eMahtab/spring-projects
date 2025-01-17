# 

### @Autowired

Marks a constructor, field, setter method, or config method as to be autowired by Spring's dependency injection facilities. This is an alternative to the JSR-330 [Inject](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/inject/Inject.html) annotation, adding required-vs-optional semantics.

### @Bean 

@Bean indicates that a method produces a bean to be managed by the Spring container. By default, the bean name will be the same as the method name.

### @Configuration

@Configuration indicates that a class declares one or more @Bean methods and may be processed
by the Spring container to generate bean definitions and service requests for those beans at runtime, for example:

```java
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
```

### Note : @ComponentScan

In above example we used @Configuration and @Bean methods to explicitly define all the beans (GreetingService, FieldInjectionExample, SetterInjectionExample, and ConstructorInjectionExample). We didn't use the @ComponentScan annotation.

@ComponentScan is only necessary if you rely on Spring to automatically discover and register components annotated with @Component, @Service, @Repository, or @Controller.

### AnnotationConfigApplicationContext

Standalone application context, accepting component classes as input — in particular @Configuration-annotated classes, but also plain @Component types and JSR-330 compliant classes using jakarta.inject annotations.

```java
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
```

In above main method, AnnotationConfigApplicationContext class scans the specified configuration class (AppConfig) for @Bean methods.
All beans explicitly defined in this class using @Bean methods are registered.

We can also pass a package as an argument to AnnotationConfigApplicationContext class constructor, in that case it will perform a component scan within the specified package. It registers beans annotated with @Component, @Service, @Repository, @Controller, etc.


# References :
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html

https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html

https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Autowired.html

https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/AnnotationConfigApplicationContext.html
