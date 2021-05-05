package com.github.exceptions;

public class UserInsertException extends RuntimeException {

    public UserInsertException() {
    }

    public UserInsertException(String message) {
        super(message);
    }
}
