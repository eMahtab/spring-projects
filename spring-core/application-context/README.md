### spring-core

Basic building block for Spring that in conjunction with Spring Beans provides dependency injection and IoC features.

### spring-context

Spring Context provides access to configured objects like a registry (a context). It inherits its features from Spring Beans and adds support for internationalization, event propagation, resource loading, and the transparent creation of contexts.

spring-context have dependency on spring-beans which depends on spring-core. 
So spring-context dependency will include both spring-beans and spring-core.

```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>6.2.1</version>
</dependency>
```





# References :

### ApplicationContext over BeanFactory :

https://docs.spring.io/spring-framework/docs/4.3.12.RELEASE/spring-framework-reference/html/beans.html#context-introduction-ctx-vs-beanfactory
