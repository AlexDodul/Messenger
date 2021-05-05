package com.github.service.message;

import com.github.entity.Message;
import com.github.repository.message.IMessageRepository;

import java.util.LinkedList;
import java.util.List;

public class MessageService implements IMessageService {

    private int cacheSize;

    private LinkedList<String> cache;

    private IMessageRepository messageRepository;

    public MessageService(IMessageRepository messageRepository, int cacheSize) {
        this.cacheSize = cacheSize;
        this.cache = new LinkedList<>();
        this.messageRepository = messageRepository;
    }

    @Override
    public void insert(String payload) {
        this.cache.addFirst(payload);
        if (this.cache.size() > cacheSize) {
            this.cache.removeLast();
        }
        this.messageRepository.insert(payload, "public.message");
    }

    @Override
    public List<String> getMessages(int number) {
        if (number <= this.cacheSize) {
            return this.cache.subList(0, number - 1);
        }
        List<Message> messages = this.messageRepository.getMessages(number, "public.message");
        LinkedList<String> result = new LinkedList<>();
        for (Message message : messages) {
            result.addLast(message.getPayload());
        }
        return result;
    }
}
