package com.github.micro.orm.exceptions;

public class ElementNotFoundException extends RuntimeException{

    public ElementNotFoundException() {
    }

    public ElementNotFoundException(String message) {
        super(message);
    }
}
