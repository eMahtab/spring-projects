package net.mahtabalam.exception;

public class AccountNumberNotFoundException extends RuntimeException{

    public AccountNumberNotFoundException(String message) {
        super(message);
    }
}
