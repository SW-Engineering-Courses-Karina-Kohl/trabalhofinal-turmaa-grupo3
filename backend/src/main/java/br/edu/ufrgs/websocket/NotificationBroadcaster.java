package br.edu.ufrgs.websocket;
import jakarta.websocket.Session;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationBroadcaster {

    private static volatile NotificationBroadcaster instance;
    private final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    private NotificationBroadcaster() {}

    public static NotificationBroadcaster getInstance() {
        if (instance == null) {
            synchronized (NotificationBroadcaster.class) {
                if (instance == null) {
                    instance = new NotificationBroadcaster();
                }
            }
        }
        return instance;
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    // === Main broadcast methods with type ===

    public void broadcastSuccess(String message) {
        broadcastTyped("success", message);
    }

    public void broadcastError(String message) {
        broadcastTyped("error", message);
    }

    public void broadcastWarning(String message) {
        broadcastTyped("warning", message);
    }

    public void broadcastInfo(String message) {
        broadcastTyped("info", message);
    }

    private void broadcastTyped(String type, String message) {
        String json = String.format("""
            {
                "type": "notification",
                "variant": "%s",
                "message": "%s",
                "timestamp": %d
            }
            """, type, escapeJson(message), System.currentTimeMillis());

        sessions.removeIf(s -> !s.isOpen());

        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getAsyncRemote().sendText(json);
                } catch (Exception e) {
                    sessions.remove(session);
                }
            }
        }
    }

    private String escapeJson(String str) {
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r");
    }

    // Bonus: Send custom object
    public void broadcast(Notification notification) {
        // You can expand this later with Jackson if needed
    }
}
