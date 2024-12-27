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