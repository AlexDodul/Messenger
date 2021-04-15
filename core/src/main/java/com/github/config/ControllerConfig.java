package com.github.config;

import com.github.controllers.UsersController;

public class ControllerConfig {
    public static UsersController usersController() {
        return new UsersController();
    }
}
