package com.github.micro.orm.exceptions;

public class CustomSQLException extends RuntimeException {

    public CustomSQLException() {
    }

    public CustomSQLException(String message) {
        super(message);
    }

}
