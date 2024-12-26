package net.mahtabalam;

import net.mahtabalam.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;

public class FieldInjectionExample {
    // Field Injection
    @Autowired
    private GreetingService greetingService;

    public void showGreeting() {
        System.out.println("Field Injection :" + greetingService.greet());
    }
}