package com.danieljoanol.dontbuypets.exception;

public class EmptyImageException extends Exception {
    
    public EmptyImageException(String message) {
        super(message);
    }
    
public EmptyImageException(String message, Throwable error) {
    super(message, error);
}
}
