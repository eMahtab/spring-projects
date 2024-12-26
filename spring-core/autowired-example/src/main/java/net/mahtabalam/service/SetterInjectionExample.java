package net.mahtabalam;

import org.springframework.beans.factory.annotation.Autowired;

public class SetterInjectionExample {
    private GreetingService greetingService;

    // Setter Injection
    @Autowired
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void showGreeting() {
        System.out.println("Setter Injection :" + greetingService.greet());
    }
}