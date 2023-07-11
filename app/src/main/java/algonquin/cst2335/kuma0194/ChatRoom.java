package algonquin.cst2335.kuma0194;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.kuma0194.data.ChatRoomViewModel;
import algonquin.cst2335.kuma0194.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.kuma0194.databinding.ReceiveRowBinding;
import algonquin.cst2335.kuma0194.databinding.SentRowBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages;

    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ChatRoomViewModel chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the ViewModel ArrayList
        messages = chatModel.messages;

        binding.submit.setOnClickListener(click -> {
            String typed = binding.message.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage sentMessage = new ChatMessage(typed, currentDateandTime, true);
            messages.add(sentMessage);
            binding.message.setText(""); // Remove what was typed

            myAdapter.notifyItemInserted(messages.size() - 1); // Redraw the missing row
        });

        binding.receive.setOnClickListener(click -> {
            String typed = binding.message.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage receivedMessage = new ChatMessage(typed, currentDateandTime, false);
            messages.add(receivedMessage);
            binding.message.setText(""); // Remove what was typed

            myAdapter.notifyItemInserted(messages.size() - 1); // Redraw the missing row
        });

        binding.theRecycleVIew.setAdapter(myAdapter = new RecyclerView.Adapter<RowHolder>() {
            @NonNull
            @Override
            public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentRowBinding rowBinding = SentRowBinding.inflate(getLayoutInflater(), parent, false);
                    return new RowHolder(rowBinding.getRoot());
                } else {
                    ReceiveRowBinding rowBinding = ReceiveRowBinding.inflate(getLayoutInflater(), parent, false);
                    return new RowHolder(rowBinding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull RowHolder holder, int position) {
                ChatMessage message = messages.get(position);
                holder.message.setText(message.getMessage());
                holder.time.setText(message.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage message = messages.get(position);
                return message.isSent() ? 0 : 1;
            }
        });

        binding.theRecycleVIew.setLayoutManager(new LinearLayoutManager(this));
    }

    // This represents one row
    class RowHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView time;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.theMessage);
            time = itemView.findViewById(R.id.theTime);
        }
    }
}
