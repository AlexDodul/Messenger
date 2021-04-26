package com.github.service.message;

import java.util.Arrays;
import java.util.List;

public class MessageServiceTestMock {

    private static String[] messages = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

    private static final List<String> manyMessages = Arrays.asList(messages);

    public static List<String> getManyMessages() {
        return manyMessages;
    }
}
