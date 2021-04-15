package com.github.service;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;

public interface IUserService {

    User findAuth(UserAuthDto userAuthDto);

    User insert(UserRegDto userRegDto);

}
