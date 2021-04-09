package com.github.handlers;

import com.github.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UsersHandler implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserService userService;

    public UsersHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Before call to method: {}, with args: {}", method.getName(), args);
        Object result = method.invoke(this.userService, args);
        log.info("After call to method: {}",  result);
        return result;
    }
}
