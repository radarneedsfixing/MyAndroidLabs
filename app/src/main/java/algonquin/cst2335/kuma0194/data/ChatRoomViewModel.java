package algonquin.cst2335.kuma0194.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.kuma0194.ChatMessage;

public class ChatRoomViewModel extends ViewModel {

    public ArrayList<ChatMessage> messages = new ArrayList<>();
    public MutableLiveData<ChatMessage> selectedMessage = new MutableLiveData<>();


    public ArrayList<ChatMessage> getMessages() {
        return messages;
    }
}
