package com.github.repository.message;

import com.github.entity.Message;
import com.github.micro.orm.CustomRowMapper;

public class MessageRowMapper {

    public static CustomRowMapper<Message> getRowMapper(){
        return resultSet -> new Message(
                resultSet.getLong("id"),
                resultSet.getString("payload")
        );
    }
}
