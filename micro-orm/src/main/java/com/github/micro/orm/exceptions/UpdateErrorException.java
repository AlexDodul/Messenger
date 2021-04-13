package com.github.micro.orm.exceptions;

public class UpdateErrorException extends RuntimeException {

    public UpdateErrorException() {
    }

    public UpdateErrorException(String message) {
        super(message);
    }

}
