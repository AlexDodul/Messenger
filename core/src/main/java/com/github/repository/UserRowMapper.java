package com.github.repository;

import com.github.entity.User;
import com.github.micro.orm.CustomRowMapper;

public class UserRowMapper {

    public static CustomRowMapper<User> getRowMapper(){
        return resultSet -> new User(
                resultSet.getLong("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("phone")
        );
    }

}
