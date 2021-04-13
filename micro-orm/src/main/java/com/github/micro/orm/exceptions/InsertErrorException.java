package com.github.micro.orm.exceptions;

public class InsertErrorException extends RuntimeException{

    public InsertErrorException() {
    }

    public InsertErrorException(String message) {
        super(message);
    }

}
