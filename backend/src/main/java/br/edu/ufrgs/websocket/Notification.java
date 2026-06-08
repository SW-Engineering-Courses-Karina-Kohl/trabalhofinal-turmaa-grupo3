package br.edu.ufrgs.websocket;

public class Notification {
    private String variant;
    private String message;
    private long timestamp;

    public Notification(String variant, String message) {
        this.variant = variant;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getVariant() { return variant; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}
