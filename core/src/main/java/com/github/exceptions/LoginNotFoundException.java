package com.github.exceptions;

public class LoginNotFoundException extends RuntimeException{

    public LoginNotFoundException() {
    }

    public LoginNotFoundException(String message) {
        super(message);
    }

}
