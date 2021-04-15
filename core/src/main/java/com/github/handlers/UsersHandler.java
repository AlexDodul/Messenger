package com.github.handlers;

import com.github.controllers.UserController;
import com.github.dto.UserAuthDto;
import com.github.exceptions.BadRequest;
import com.github.utils.JsonHelper;
import com.github.utils.http.Status;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class UsersHandler extends HttpServlet {

    private UserController userController;

    public UsersHandler(UserController userController) {
        this.userController = userController;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletOutputStream out = resp.getOutputStream();
//        Token result = this.userController.auth(new UserAuthDto());
//        String str = JsonHelper.toJson(result).orElseThrow(BadRequest::new);
        out.write("str".getBytes());
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (!"application/json".equalsIgnoreCase(req.getHeader("Content-Type"))) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Invalid content type.");
        } else {
            ServletOutputStream out = resp.getOutputStream();
            String body = req.getReader().lines().collect(Collectors.joining());
            UserAuthDto payload = JsonHelper.fromJson(body, UserAuthDto.class)
                    .orElseThrow(BadRequest::new);
//            Token result = this.userController.auth(payload);
//            String str = JsonHelper.toJson(result).orElseThrow(BadRequest::new);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.write("str".getBytes());
            out.flush();
            out.close();
        }

    }
}
