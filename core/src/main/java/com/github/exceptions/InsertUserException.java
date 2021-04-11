package com.github.exceptions;

public class InsertUserException extends RuntimeException{

    public InsertUserException() {
    }

    public InsertUserException(String message) {
        super(message);
    }
}
