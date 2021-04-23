package com.github.handlers;


import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

public class WebsocketHandler {

    @OnMessage
    public void onMessage(Session session, String payload){

        System.out.println(payload);
        try {
            session.getBasicRemote().sendText(payload);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig){
        System.out.println(session.getId());
    }

}
