### Spring Core (spring-core)

Basic building block for Spring that in conjunction with Spring Beans provides dependency injection and IoC features.

### Spring Beans (spring-beans)

Spring Beans provides the configuration framework and basic functionality to instantiate, configure, and assemble java objects.

### Spring Context (spring-context)

Spring Context provides access to configured objects like a registry (a context). It inherits its features from Spring Beans and adds support for internationalization, event propagation, resource loading, and the transparent creation of contexts.

**spring-context have dependency on spring-beans which depends on spring-core. 
So spring-context dependency will include both spring-beans and spring-core.**

```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>6.2.1</version>
</dependency>
```

```java
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserService userService = context.getBean(UserService.class);
        User user = new User(1L,"mahtab");
        user.setName("Mahtab Alam");
        user.setEmail("mahtab@example.com");

        userService.saveUser(user);

        User retrievedUser = userService.getUserById(1L);
        System.out.println("Retrieved User: " + retrievedUser);

        ((ClassPathXmlApplicationContext) context).close();
    }
}
```



# References :

### ApplicationContext over BeanFactory :

https://docs.spring.io/spring-framework/docs/4.3.12.RELEASE/spring-framework-reference/html/beans.html#context-introduction-ctx-vs-beanfactory
