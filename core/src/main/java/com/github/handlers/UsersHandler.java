package com.github.handlers;

import com.github.controllers.UsersController;
import com.github.dto.UserAuthDto;
import com.github.exceptions.BadRequest;
import com.github.payload.Token;
import com.github.service.IUserService;
import com.github.service.UserService;
import com.github.utils.JsonHelper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

public class UsersHandler extends HttpServlet implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private IUserService userService;

    private UsersController usersController;

    public UsersHandler(UsersController usersController) {
        this.usersController = usersController;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Before call to method: {}, with args: {}", method.getName(), args);
        Object result = method.invoke(this.userService, args);
        log.info("After call to method: {}", result);
        return result;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } catch (BadRequest e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid body");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        Token result = this.usersController.auth(new UserAuthDto());
        String str = JsonHelper.toJson(result).orElseThrow(BadRequest::new);
        out.write(str.getBytes());
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!"application/json".equalsIgnoreCase(req.getHeader("Content-Type"))) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Invalid content type.");
        } else {
            ServletOutputStream out = resp.getOutputStream();
            String body = req.getReader().lines().collect(Collectors.joining());
            UserAuthDto payload = JsonHelper.fromJson(body, UserAuthDto.class)
                    .orElseThrow(BadRequest::new);
            Token result = this.usersController.auth(payload);
            String str = JsonHelper.toJson(result).orElseThrow(BadRequest::new);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.write(str.getBytes());
            out.flush();
            out.close();
        }

    }
}
