package com.github.config;

import com.github.repository.user.IUserRepository;
import com.github.repository.user.UserRepository;
import com.github.service.IUserService;
import com.github.service.UserService;

public class AppConfig {

    private static IUserRepository userRepository = new UserRepository(DatabaseConfig.getDataSource());

    private static IUserService userService = new UserService(userRepository);

    public static IUserRepository getUserRepository() {
        return userRepository;
    }

    public static IUserService getUserService() {
        return userService;
    }



}
