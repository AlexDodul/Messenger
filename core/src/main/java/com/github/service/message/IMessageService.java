package com.github.service.message;

import java.util.List;

public interface IMessageService {

    List<String> getCache();

    void insert(String payload);

    List<String> getMessages(int number);

}
