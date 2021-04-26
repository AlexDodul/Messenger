package com.github.repository.message;

import com.github.entity.Message;

import java.util.List;

public interface IMessageRepository {

    void insert(String payload);

    List<Message> getMessages(int number);

}
