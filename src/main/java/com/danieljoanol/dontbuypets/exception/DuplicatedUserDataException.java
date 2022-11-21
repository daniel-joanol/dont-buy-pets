package com.danieljoanol.dontbuypets.exception;

public class DuplicatedUserDataException extends Exception {

    public DuplicatedUserDataException(String message) {
        super(message);
    }

    public DuplicatedUserDataException(String message, Throwable error) {
        super(message, error);
    }
    
}
