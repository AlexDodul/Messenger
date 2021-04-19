package com.github.controllers;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;

public interface IUserController {

    String authorize(UserAuthDto userAuthDto);

    void register(UserRegDto userRegDto);

}
