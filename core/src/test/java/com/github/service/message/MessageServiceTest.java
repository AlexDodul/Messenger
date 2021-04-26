package com.github.service.message;

import com.github.repository.message.IMessageRepository;
import com.github.repository.message.MessageRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageServiceTest {

    private static HikariConfig config = new HikariConfig("src/test/resources/hikari.properties");

    private static HikariDataSource dataSource = new HikariDataSource(config);

    private static IMessageRepository messageRepository = new MessageRepository(dataSource);

    private static IMessageService messageService = new MessageService(messageRepository, 20);


    @Test
    public void test() {

    }

}