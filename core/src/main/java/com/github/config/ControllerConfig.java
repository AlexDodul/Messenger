package com.github.config;

import com.github.controllers.UserController;

public class ControllerConfig {
    public static UserController usersController() {
        return new UserController(AppConfig.getUserService());
    }
}
