package com.github.handlers;

import com.github.controllers.IUserController;
import com.github.exceptions.BadRequest;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class UsersHandler extends HttpServlet {

    private IUserController userController;

    public UsersHandler(IUserController userController) {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String url = req.getRequestURI();

        if(url.equals("/reg")){
            ServletOutputStream out = resp.getOutputStream();
            String body = req.getReader().lines().collect(Collectors.joining());
        } else if(url.equals("/auth")){
            ServletOutputStream out = resp.getOutputStream();
        } else {

        }




//        if (!"application/json".equalsIgnoreCase(req.getHeader("Content-Type"))) {
//            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Invalid content type.");
//        } else {
//            ServletOutputStream out = resp.getOutputStream();
//            String body = req.getReader().lines().collect(Collectors.joining());
//            UserAuthDto payload = JsonHelper.fromJson(body, UserAuthDto.class)
//                    .orElseThrow(BadRequest::new);
////            Token result = this.userController.auth(payload);
////            String str = JsonHelper.toJson(result).orElseThrow(BadRequest::new);
//            resp.setContentType("application/json");
//            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
//            out.write("str".getBytes());
//            out.flush();
//            out.close();
//        }

    }
}
