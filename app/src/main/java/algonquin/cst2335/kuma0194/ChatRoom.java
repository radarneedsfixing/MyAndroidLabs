package algonquin.cst2335.kuma0194;

import static algonquin.cst2335.kuma0194.ChatMessageDAO.*;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.kuma0194.data.ChatRoomViewModel;
import algonquin.cst2335.kuma0194.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.kuma0194.databinding.MessageDetailsLayoutBinding;
import algonquin.cst2335.kuma0194.databinding.ReceiveRowBinding;
import algonquin.cst2335.kuma0194.databinding.SentRowBinding;

public class ChatRoom extends AppCompatActivity {

    ChatMessageDAO myDAO;

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages;
    private ChatMessageDAO chatMessageDao;
    ChatRoomViewModel chatModel;

    MessageDatabase myDb;
    RecyclerView.Adapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDb = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();

         myDAO = myDb.cmDAO();
         chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

         chatModel.selectedMessage.observe(this, newMessage ->{
             //new message is what is posted to the value

             MessageDetailsFragment detailsFragment= new MessageDetailsFragment(newMessage);

             FragmentManager fMgr = getSupportFragmentManager();

             FragmentTransaction tx=  fMgr.beginTransaction();

             tx.add(  R.id.fragmentlocation,detailsFragment);
             tx.commit();


         });
        messages= chatModel.messages;

        Executor thread = Executors.newSingleThreadExecutor();







        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Initialize the ViewModel ArrayList
        messages = chatModel.messages;

        binding.submit.setOnClickListener(click -> {
            String typed = binding.message.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
            String currentDateandTime = sdf.format(new Date());


            ChatMessage sentMessage = new ChatMessage(typed, currentDateandTime, true);
            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(new Runnable() {
                @Override
                public void run() {
                    sentMessage.id =myDAO.insertMessage(sentMessage);

                }


            });



            messages.add(sentMessage);

            binding.message.setText(""); // Remove what was typed


            myAdapter.notifyItemInserted(messages.size() - 1); // Redraw the missing row
        });

        binding.receive.setOnClickListener(click -> {
            String typed = binding.message.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
            String currentDateandTime = sdf.format(new Date());




            ChatMessage receivedMessage = new ChatMessage(typed, currentDateandTime, false);



            Executor thread2 = Executors.newSingleThreadExecutor();
            thread2.execute(new Runnable() {
                @Override
                public void run() {
                    receivedMessage.id =myDAO.insertMessage(receivedMessage);

                }


            });





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

           itemView.setOnClickListener(click -> {

               /*int index = getAbsoluteAdapterPosition();
               ChatMessage selectedMessage = messages.get(index);
               chatModel.selectedMessage.postValue(messages.get(index));
*/



                int index = getAbsoluteAdapterPosition(); // Change to int
                ChatMessage toDelete = messages.get(index);

                // Backup the message to be deleted
                ChatMessage backupMessage = new ChatMessage(toDelete.getMessage(), toDelete.getTimeSent(), toDelete.isSent());

                Snackbar snackbar = Snackbar.make(itemView, "Delete this message?", Snackbar.LENGTH_LONG)
                        .setAction("DELETE", v -> {
                            // Delete the message from the list
                            messages.remove(index);
                            myAdapter.notifyItemRemoved(index);

                            // Delete the message from the database
                            Executor deleteThread = Executors.newSingleThreadExecutor();
                            deleteThread.execute(() -> myDAO.deleteMessage(toDelete));

                            // Show the Snackbar with the "Undo" action after deletion
                            Snackbar.make(itemView, "Message deleted", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", v1 -> {
                                        // Re-insert the deleted message back into the list and database
                                        messages.add(index, backupMessage);
                                        myAdapter.notifyItemInserted(index);

                                        Executor insertThread = Executors.newSingleThreadExecutor();
                                        insertThread.execute(() -> myDAO.insertMessage(backupMessage));
                                    })
                                    .show();
                        });

                snackbar.show();

            });
            message = itemView.findViewById(R.id.theMessage);
            time = itemView.findViewById(R.id.theTime);
        }
    }
}
