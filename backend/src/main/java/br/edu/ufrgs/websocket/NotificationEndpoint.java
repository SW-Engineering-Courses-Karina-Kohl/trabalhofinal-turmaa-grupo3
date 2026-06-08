package br.edu.ufrgs.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/notifications")
public class NotificationEndpoint {

    private static final NotificationBroadcaster broadcaster = NotificationBroadcaster.getInstance();

    @OnOpen
    public void onOpen(Session session) {
        broadcaster.addSession(session);
        System.out.println("New WebSocket client connected: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        broadcaster.removeSession(session);
        System.out.println("WebSocket client disconnected: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        broadcaster.removeSession(session);
        System.err.println("WebSocket error: " + throwable.getMessage());
    }

    // Optional: allow client to send messages (e.g. ping)
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received from client " + session.getId() + ": " + message);
    }
}
