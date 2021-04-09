package com.github.service;

public class UserService {
    private UserService(){

    }

    private static UserService userService;

    public static UserService getUserService() {
        return userService;
    }
}
