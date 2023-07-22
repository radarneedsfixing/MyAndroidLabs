package algonquin.cst2335.kuma0194;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.kuma0194.databinding.MessageDetailsLayoutBinding;

public class MessageDetailsFragment  extends Fragment {

    ChatMessage thisMessage;

    public MessageDetailsFragment(ChatMessage toShow)
    {
        thisMessage =toShow;

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle instance){

        MessageDetailsLayoutBinding binding = MessageDetailsLayoutBinding.inflate(inflater);

        binding.messageText.setText(thisMessage.message);
        binding.timeText.setText(thisMessage.timeSent);
        binding.idText.setText(Long.toString(thisMessage.id));

        return binding.getRoot();

    }
}
