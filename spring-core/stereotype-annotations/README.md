# Using stereotype annotations

Spring provides stereotype annotations: @Component, @Service, and @Controller etc.

**@Component** is a generic stereotype for any Spring-managed component. 

**@Repository, @Service, and @Controller are specializations of @Component** for more specific use cases (in the persistence, service, and presentation layers, respectively). Therefore, you can annotate your component classes with @Component, but, by annotating them with @Repository, @Service, or @Controller instead, your classes are more properly suited for processing by tools or associating with aspects. 

For example, these stereotype annotations make ideal targets for pointcuts. @Repository, @Service, and @Controller may also carry additional semantics in future releases of the Spring Framework. 

**Thus, if you are choosing between using @Component or @Service for your service layer, @Service is clearly the better choice. Similarly, as stated earlier, @Repository is already supported as a marker for automatic exception translation in your persistence layer.**

**@Component :** Indicates that the annotated class is a component. Such classes are considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.

```java
@Repository
public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    public User findById(Long id) {
        return users.get(id);
    }

    public void save(User user) {
        users.put(user.getId(), user);
    }
}
```

```java
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
```

```java
package net.mahtabalam;

import net.mahtabalam.model.User;
import net.mahtabalam.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("net.mahtabalam");
        UserService userService = context.getBean(UserService.class);
        User user = new User(1L, "mahtab");
        user.setName("Mahtab Alam");
        user.setEmail("mahtab@example.com");

        userService.saveUser(user);

        User retrievedUser = userService.getUserById(1L);
        System.out.println("Retrieved User: " + retrievedUser);
    }
}
```

# References :

https://docs.spring.io/spring-framework/reference/core/beans/classpath-scanning.html
