package com.github.config;

import com.github.handlers.UsersHandler;
import com.github.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ServerConfig {
    public static UserService userService(){
        UserService origin = UserService.getUserService();
        InvocationHandler handler = new UsersHandler(origin);
        return (UserService) Proxy.newProxyInstance(origin.getClass().getClassLoader(),
                new Class[] {UserService.class}, handler);
    }
}
