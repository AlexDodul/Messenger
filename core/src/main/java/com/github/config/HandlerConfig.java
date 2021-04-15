package com.github.config;

import com.github.handlers.UsersHandler;

public class HandlerConfig {
    public static UsersHandler usersHandler(){
        return new UsersHandler(ControllerConfig.usersController());
    }

}
