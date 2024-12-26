# 

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
