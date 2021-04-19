package com.github.controllers;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.payload.Token;
import com.github.service.UserService;
import com.github.utils.TokenProvider;

import java.util.Date;

public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }


    public String auth(UserAuthDto userAuthDto) {
        User user = this.userService.findAuth(userAuthDto);
        return TokenProvider.encode(
                new Token(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        new Date(System.currentTimeMillis() + 1800000),
                        new Date()
                ));
    }

    public void reg(UserRegDto userRegDto){
        this.userService.insert(userRegDto);

    }
}
