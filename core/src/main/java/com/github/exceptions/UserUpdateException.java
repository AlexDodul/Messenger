package com.github.exceptions;

public class UserUpdateException extends RuntimeException{

    public UserUpdateException() {
    }

    public UserUpdateException(String message) {
        super(message);
    }

}
