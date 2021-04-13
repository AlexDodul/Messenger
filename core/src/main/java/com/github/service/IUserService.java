package com.github.service;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;

import java.util.Collection;

public interface IUserService {

    User findAuth(UserAuthDto userAuthDto);

    User findReg(UserRegDto userRegDto);

    void insert(UserRegDto userRegDto);

    void update(User user);

    void delete(User user);

}
