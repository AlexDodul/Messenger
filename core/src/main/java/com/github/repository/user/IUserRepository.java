package com.github.repository.user;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;

import java.util.Collection;

public interface IUserRepository {

    Collection<User> findAll(String tableName);

    User findAuth(UserAuthDto userAuthDto, String tableName);

    User findReg(UserRegDto userRegDto, String tableName);

    User insert(UserRegDto userRegDto, String tableName);

    User update(User user, String tableName);

    void delete(User user, String tableName);

    void deleteAll(String tableName);
}
