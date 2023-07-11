package algonquin.cst2335.kuma0194;
public class ChatMessage {
    private String message;
    private String timeSent;
    private boolean sent;

    public ChatMessage(String message, String timeSent, boolean sent) {
        this.message = message;
        this.timeSent = timeSent;
        this.sent = sent;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSent() {
        return sent;
    }
}
