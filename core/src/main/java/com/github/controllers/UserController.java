package com.github.controllers;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.exceptions.KeyGenerationException;
import com.github.payload.Token;
import com.github.service.IUserService;
import com.github.utils.TokenProvider;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class UserController implements IUserController {

    IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String authorize(UserAuthDto userAuthDto) {

        User user = this.userService.findAuth(userAuthDto);
        KeyPairGenerator keyPairGenerator;
        KeyPair keyPair;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        Token token = new Token(
                user.getLogin(),
                user.getFirstName(),
                user.getLastName(),
                new Date(System.currentTimeMillis() + 1800000),
                new Date(),
                keyPair.getPublic()
        );
        return TokenProvider.encode(token, keyPair.getPrivate());
        } catch (NoSuchAlgorithmException e) {
            throw new KeyGenerationException(e.getMessage());
        }
    }

    @Override
    public void register(UserRegDto userRegDto) {
        this.userService.insert(userRegDto);
    }
}
