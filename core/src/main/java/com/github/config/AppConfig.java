package com.github.config;

import com.github.controllers.IUserController;
import com.github.controllers.UserController;
import com.github.handlers.UsersHandler;
import com.github.handlers.WebsocketHandler;
import com.github.network.Broker;
import com.github.network.WebsocketConnectionPool;
import com.github.proxy.UserServiceProxy;
import com.github.repository.user.IUserRepository;
import com.github.repository.user.UserRepository;
import com.github.service.user.IUserService;
import com.github.service.user.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class AppConfig {

    private static WebsocketConnectionPool websocketConnectionPool = new WebsocketConnectionPool();
    private static Broker broker = new Broker();

    private static IUserRepository userRepository = new UserRepository(DatabaseConfig.getDataSource());

    private static IUserService userService = new UserService(getUserRepository());

    private static IUserController userController = new UserController(getUserService());

    private static UsersHandler usersHandler = new UsersHandler(getUserController());

    private static WebsocketHandler websocketHandler = new WebsocketHandler(getWebsocketConnectionPool(), getBroker());

    public static IUserRepository getUserRepository() {
        return userRepository;
    }

    public static IUserService getUserService() {
        InvocationHandler handler = new UserServiceProxy(userService);
        return (IUserService) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                new Class[]{IUserService.class},
                handler
        );
    }

    public static IUserController getUserController() {
        return userController;
    }

    public static UsersHandler getUsersHandler() {
        return usersHandler;
    }

    public static WebsocketHandler getWebsocketHandler() {
        return websocketHandler;
    }

    public static WebsocketConnectionPool getWebsocketConnectionPool() {
        return websocketConnectionPool;
    }

    public static Broker getBroker() {
        return broker;
    }
}
