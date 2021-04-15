package com.github.controllers;

import com.github.utils.http.Status;

public class UserController implements IUserController {

    public UserController() {

    }

    @Override
    public Status authorize(String body) {
        return new Status(200, "OK");
    }

    @Override
    public Status register(String body) {
        return new Status(200, "OK");
    }

}
