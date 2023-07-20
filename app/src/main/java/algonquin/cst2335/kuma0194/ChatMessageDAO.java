package algonquin.cst2335.kuma0194;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessageDAO {
    @Insert
    long insertMessage(ChatMessage message);

    @Query("SELECT * FROM CHAT_MESSAGES")
     public List<ChatMessage> getAllMessages();
    @Delete
    void deleteMessage(ChatMessage message);
}
