package com.github.config;

import com.github.controllers.IUserController;
import com.github.controllers.UserController;
import com.github.handlers.UsersHandler;
import com.github.repository.user.IUserRepository;
import com.github.repository.user.UserRepository;
import com.github.service.IUserService;
import com.github.service.UserService;

public class AppConfig {

    private static IUserRepository userRepository = new UserRepository(DatabaseConfig.getDataSource());

    private static IUserService userService = new UserService(userRepository);

    private static IUserController userController = new UserController(userService);

    private static UsersHandler usersHandler = new UsersHandler(userController);

    public static IUserRepository getUserRepository() {
        return userRepository;
    }

    public static IUserService getUserService() {
        return userService;
    }

    public static IUserController getUserController(){
        return userController;
    }

    public static UsersHandler getUsersHandler() {
        return usersHandler;
    }

}
