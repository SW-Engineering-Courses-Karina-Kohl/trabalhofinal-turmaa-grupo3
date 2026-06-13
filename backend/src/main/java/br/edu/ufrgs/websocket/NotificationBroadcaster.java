package br.edu.ufrgs.websocket;
import br.edu.ufrgs.model.CommissionReport;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.annotation.JsonbProperty;
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
    public class WelcomeNotification {
        @JsonbProperty("type")
        public String type = "welcome";

        public WelcomeNotification() {}


        public String getType() {
            return type;
        }
    }
    public void broadcastWelcome() {
        WelcomeNotification notification = new WelcomeNotification();
        try (Jsonb jsonb = JsonbBuilder.create()) {
            broadcast(jsonb.toJson(notification));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class CommissionReportNotification {
        @JsonbProperty("type")
        public String type;

        @JsonbProperty("fileName")
        public String fileName;

        public CommissionReportNotification() {}

        public CommissionReportNotification(String type, String fileName) {
            this.type = type;
            this.fileName = fileName;
        }

        public String getType() {
            return type;
        }

        public String getFileName() {
            return fileName;
        }
    }
    public void broadcastProcessingCommissionReport(CommissionReport report) {
        CommissionReportNotification notification = new CommissionReportNotification("processing", report.getFilename());
        try (Jsonb jsonb = JsonbBuilder.create()) {
            broadcast(jsonb.toJson(notification));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void broadcastProcessedCommissionReport(CommissionReport report) {
        CommissionReportNotification notification = new CommissionReportNotification("processed", report.getFilename());
        try (Jsonb jsonb = JsonbBuilder.create()) {
            broadcast(jsonb.toJson(notification));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ExportFileNotification {
        @JsonbProperty("type")
        public String type;

        @JsonbProperty("url")
        public String url;

        @JsonbProperty("fileName")
        public String fileName;

        public ExportFileNotification() {}

        public ExportFileNotification(String type, String url, String fileName) {
            this.type = type;
            this.url = url;
            this.fileName = fileName;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public String getFileName() {
            return fileName;
        }
    }
    public void broadcastExportFile(String docType, String endpoint, String filename) {
        ExportFileNotification exportFile = new ExportFileNotification(docType, endpoint, filename);
        try (Jsonb jsonb = JsonbBuilder.create()) {
            broadcast(jsonb.toJson(exportFile));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcast(String str) {
        sessions.removeIf(s -> !s.isOpen());
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getAsyncRemote().sendText(str);
                } catch (Exception e) {
                    sessions.remove(session);
                }
            }
        }
    }
}
