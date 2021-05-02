package com.github.service.user;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;

import java.util.Collection;

public interface IUserService {

    Collection<User> findAll();

    User findAuth(UserAuthDto userAuthDto);

    User findReg(UserRegDto userRegDto);

    User insert(UserRegDto userRegDto);

    User update(User user);

    void delete(User user);

    void deleteAll();

}
