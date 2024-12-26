package net.mahtabalam;

import org.springframework.beans.factory.annotation.Autowired;

public class ConstructorInjectionExample {
    private final GreetingService greetingService;

    // Constructor Injection
    @Autowired
    public ConstructorInjectionExample(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void showGreeting() {
        System.out.println("Constructor Injection :" + greetingService.greet());
    }
}