package com.github.exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }
}
