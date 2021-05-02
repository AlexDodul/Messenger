package com.github.entity;

import java.io.Serializable;

public class Message implements Serializable {

    private long id;

    private String payload;

    public Message(long id, String payload) {
        this.id = id;
        this.payload = payload;
    }

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}
