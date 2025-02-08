package net.mahtabalam.exception;

public class MissingRequiredFieldException extends RuntimeException {
    public MissingRequiredFieldException(String message) {
        super(message);
    }
}