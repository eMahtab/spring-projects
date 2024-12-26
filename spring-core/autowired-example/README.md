# 

### @Autowired

Marks a constructor, field, setter method, or config method as to be autowired by Spring's dependency injection facilities. This is an alternative to the JSR-330 [Inject](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/inject/Inject.html) annotation, adding required-vs-optional semantics.

### @Bean 

@Bean indicates that a method produces a bean to be managed by the Spring container.

### @Configuration

@Configuration indicates that a class declares one or more @Bean methods and may be processed
by the Spring container to generate bean definitions and service requests for those beans at runtime, for example:

```java
@Configuration
 public class AppConfig {

     @Bean
     public MyBean myBean() {
         // instantiate, configure and return bean ...
     }
 }
```


# References :
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html

https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Autowired.html
