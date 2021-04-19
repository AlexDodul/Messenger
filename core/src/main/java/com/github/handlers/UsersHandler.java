package com.github.handlers;

import ch.qos.logback.core.subst.Token;
import com.github.controllers.IUserController;
import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.exceptions.*;
import com.github.utils.JsonHelper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String url = req.getRequestURI();
            PrintWriter writer = resp.getWriter();
            if (url.equals("/auth")) {
                resp.setContentType("text/html");
                String html = Files.readString(Path.of(System.getProperty("user.dir") + "/core/src/main/resources/web/tologin.html"), StandardCharsets.US_ASCII);
                resp.setContentLength(html.length() + 1);
                writer.write(html);
            } else if (url.equals("/reg")) {
                resp.setContentType("text/html");
                String html = Files.readString(Path.of(System.getProperty("user.dir") + "/core/src/main/resources/web/toregister.html"), StandardCharsets.US_ASCII);
                resp.setContentLength(html.length() + 1);
                writer.write(html);
            } else {
                resp.setStatus(404);
            }
            writer.flush();
            writer.close();
        } catch (IOException e){
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String url = req.getRequestURI();

        if (url.equals("/auth")) {
            try {
                ServletOutputStream out = resp.getOutputStream();
                String body = req.getReader().lines().collect(Collectors.joining());
                UserAuthDto payload = JsonHelper.fromJson(body, UserAuthDto.class)
                        .orElseThrow(BadRequest::new);
                String result = this.userController.authorize(payload);
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                out.write(result.getBytes());
                out.flush();
                out.close();
            } catch (DatabaseException | IOException e) {
                resp.setStatus(500);
            } catch (BadRequest e) {
                resp.setStatus(400);
            } catch (WrongLoginException | WrongPasswordException e) {
                resp.setStatus(401);
            }
        } else if (url.equals("/reg")) {
            try {
                ServletOutputStream out = resp.getOutputStream();
                String body = req.getReader().lines().collect(Collectors.joining());
                System.out.println(body);
                try {
                    UserRegDto payload = JsonHelper.fromJson(body, UserRegDto.class)
                            .orElseThrow(BadRequest::new);
                    this.userController.register(payload);
                    resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                } catch (DatabaseException e) {
                    resp.setStatus(500);
                } catch (BadRequest e) {
                    resp.setStatus(400);
                } catch (UserInsertException e) {
                    resp.setStatus(409);
                }
                out.flush();
                out.close();
            } catch (DatabaseException | IOException e) {
                resp.setStatus(500);
            } catch (BadRequest e) {
                resp.setStatus(400);
            } catch (WrongLoginException | WrongPasswordException e) {
                resp.setStatus(401);
            }
        } else {
            resp.setStatus(404);
        }

    }
}
