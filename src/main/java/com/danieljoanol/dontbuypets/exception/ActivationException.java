package com.danieljoanol.dontbuypets.exception;

public class ActivationException extends Exception {

    public ActivationException(String message) {
        super(message);
    }
    
    public ActivationException(String message, Throwable error) {
        super(message, error);
    }

}
