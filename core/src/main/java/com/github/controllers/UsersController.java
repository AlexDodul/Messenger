package com.github.controllers;

import com.github.dto.UserAuthDto;
import com.github.payload.Token;

import java.util.Date;

public class UsersController {
    public Token auth(UserAuthDto payload) {
        return new Token(1L, "First Name", "Last Name", new Date(), new Date());
    }
}
