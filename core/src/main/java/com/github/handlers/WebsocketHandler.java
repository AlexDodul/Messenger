package com.github.handlers;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/user/chat")
public class WebsocketHandler {

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println(session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String payload) throws IOException {
        System.out.println(payload);
        session.getBasicRemote().sendText(payload);
    }
}
