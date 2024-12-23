package com.adesso.project.awesomepizza.exception;

public class PizzaValidationException extends RuntimeException {
    public PizzaValidationException(String message) {
        super(message);
    }
}