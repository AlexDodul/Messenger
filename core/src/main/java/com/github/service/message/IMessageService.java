package com.github.service.message;

import java.util.List;

public interface IMessageService {

    void insert(String payload);

    List<String> getMessages(int number);

}
