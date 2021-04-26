package com.github.handlers;

import com.github.network.Broker;
import com.github.network.WebsocketConnectionPool;
import com.github.payload.Envelope;
import com.github.payload.Token;
import com.github.utils.JsonHelper;
import com.github.utils.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnMessage;
import javax.websocket.Session;

public class WebsocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebsocketHandler.class);

    private final WebsocketConnectionPool websocketConnectionPool;

    private final Broker broker;

    public WebsocketHandler(WebsocketConnectionPool websocketConnectionPool, Broker broker) {
        this.websocketConnectionPool = websocketConnectionPool;
        this.broker = broker;
    }

    @OnMessage
    public void messages(Session session, String payload) {
        try {
            Envelope envelope = JsonHelper.fromJson(payload, Envelope.class).orElseThrow();
            //TODO;: replace switch by Map (key - instances)
            switch (envelope.getTopic()) {
                case auth:
                    Token result = TokenProvider.decode(envelope.getPayload());
                    String login = result.getLogin();
                    websocketConnectionPool.addSession(login, session);
                    break;
                case messages:
                    broker.broadcast(websocketConnectionPool.getSessions(), envelope);
                    break;
            }
        } catch (Throwable e) {
            //TODO: single send to user about error
            log.warn("Enter {}", e.getMessage());
        }
    }
}

