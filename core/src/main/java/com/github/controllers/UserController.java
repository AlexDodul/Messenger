package com.github.controllers;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.exceptions.*;
import com.github.service.IUserService;
import com.github.utils.JsonHelper;
import com.github.utils.http.Status;
import com.github.utils.http.StatusFactory;

public class UserController implements IUserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Status authorize(String body) {

        UserAuthDto authDto;

        try {
            authDto = JsonHelper.fromJson(body, UserAuthDto.class).orElseThrow(BadRequest::new);
        } catch (BadRequest e){
            return StatusFactory.getStatus(400, "Bad request");
        }
        try {
            this.userService.findAuth(authDto);
        } catch (DatabaseException e){
            return StatusFactory.getStatus(500, "Internal Server Error");
        } catch (WrongLoginException | WrongPasswordException e){
            return StatusFactory.getStatus(403, "Forbidden");
        }
        return StatusFactory.getStatus(200, "OK");
    }

    @Override
    public Status register(String body) {

        UserRegDto regDto;

        try {
            regDto = JsonHelper.fromJson(body, UserRegDto.class).orElseThrow(BadRequest::new);
        } catch (BadRequest e){
            return StatusFactory.getStatus(400, "Bad request");
        }
        try {
            this.userService.insert(regDto);
        } catch (DatabaseException e){
            return StatusFactory.getStatus(500, "Internal Server Error");
        } catch (UserInsertException e){
            return StatusFactory.getStatus(403, "Forbidden");
        }
        return StatusFactory.getStatus(200, "OK");
    }

}
