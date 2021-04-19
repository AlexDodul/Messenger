package com.github.handlers;

import com.github.controllers.UsersController;
import com.github.dto.UserAuthDto;
import com.github.exceptions.BadRequest;
import com.github.payload.Token;
import com.github.utils.JsonHelper;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class UsersHandler extends HttpServlet {

    private UsersController usersController;

    private UserAuthDto userAuthDto;

    public UsersHandler(UsersController usersController) {
        this.usersController = usersController;
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
        String result = this.usersController.auth(new UserAuthDto());
        //String str = JsonHelper.toJson(result).orElseThrow(BadRequest::new);
        out.write(result.getBytes());
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!"application/json".equalsIgnoreCase(req.getHeader("Content-Type"))) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Invalid content type.");
        } else {
            String url = req.getRequestURI();
            if (url.equals("/auth")){
                this.usersController.auth(null);
            }
            if (url.equals("/reg")){
                this.usersController.reg(null);
            }
            ServletOutputStream out = resp.getOutputStream();
            String body = req.getReader().lines().collect(Collectors.joining());
            UserAuthDto payload = JsonHelper.fromJson(body, UserAuthDto.class)
                    .orElseThrow(BadRequest::new);
            String result = this.usersController.auth(payload);
            //String str = JsonHelper.toJson(result).orElseThrow(BadRequest::new);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.write(result.getBytes());
            out.flush();
            out.close();
        }

    }
}
