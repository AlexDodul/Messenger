package com.github.controllers;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.payload.Token;
import com.github.service.IUserService;
import com.github.utils.TokenProvider;

import java.util.Date;

public class UserController implements IUserController {

    IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String authorize(UserAuthDto userAuthDto) {
        User user = this.userService.findAuth(userAuthDto);
        Token token = new Token(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                new Date(System.currentTimeMillis() + 1800000),
                new Date()
        );
        return TokenProvider.encoding(token);
    }

    @Override
    public void register(UserRegDto userRegDto) {
        this.userService.insert(userRegDto);
    }
}
