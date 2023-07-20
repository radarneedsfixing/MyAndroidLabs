package algonquin.cst2335.kuma0194;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_messages")
public class ChatMessage  {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "message")
    public String message;

    @ColumnInfo(name = "time_sent")
    public String timeSent;

    @ColumnInfo(name = "is_sent")
    public boolean sent;

    public ChatMessage(String message, String timeSent, boolean sent) {
        this.message = message;
        this.timeSent = timeSent;
        this.sent = sent;
    }
    public ChatMessage() //empty constr
    {

    }

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
