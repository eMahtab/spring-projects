package net.mahtabalam;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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