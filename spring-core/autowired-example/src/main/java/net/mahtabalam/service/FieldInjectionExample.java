package net.mahtabalam;

import org.springframework.beans.factory.annotation.Autowired;

public class FieldInjectionExample {
    // Field Injection
    @Autowired
    private GreetingService greetingService;

    public void showGreeting() {
        System.out.println("Field Injection :" + greetingService.greet());
    }
}