package com.github.proxy;

import com.github.service.user.IUserService;
import com.github.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserServiceProxy implements InvocationHandler{

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final IUserService userService;

    public UserServiceProxy(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Before call to method: {}, with args: {}", method.getName(), args);
        Object result = method.invoke(this.userService, args);
        log.info("After call to method: {}", result);
        return result;
    }

}
