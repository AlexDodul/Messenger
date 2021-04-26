package com.github.exceptions;

public class MessageInsertException extends RuntimeException {

    public MessageInsertException() {
    }

    public MessageInsertException(String message) {
        super(message);
    }

}
