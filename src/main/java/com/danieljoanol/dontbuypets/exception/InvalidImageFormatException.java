package com.danieljoanol.dontbuypets.exception;

public class InvalidImageFormatException extends Exception {

    public InvalidImageFormatException(String message) {
        super(message);
    }

    public InvalidImageFormatException(String message, Throwable error) {
        super(message, error);
    }
    
}
