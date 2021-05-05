package com.github.repository.user;

import com.github.entity.User;
import com.github.micro.orm.CustomRowMapper;

public class UserRowMapper {

    public static CustomRowMapper<User> getRowMapper() {
        return resultSet -> new User(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("nickname"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("phone")
        );
    }
}
