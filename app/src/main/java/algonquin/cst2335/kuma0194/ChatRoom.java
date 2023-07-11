package algonquin.cst2335.kuma0194;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import algonquin.cst2335.kuma0194.data.ChatRoomViewModel;
import algonquin.cst2335.kuma0194.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.kuma0194.databinding.ReceiveRowBinding;
import algonquin.cst2335.kuma0194.databinding.SentRowBinding;

public class ChatRoom extends AppCompatActivity {

     ActivityChatRoomBinding binding;
    ArrayList<String> messages;

    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ChatRoomViewModel chatModel= new ViewModelProvider(this).get(ChatRoomViewModel.class); ;


        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize to the ViewModel arraylist:

        messages = chatModel.messages;


        binding.submit.setOnClickListener(click -> {
            String typed =binding.message.getText().toString();
            messages.add(typed);
            //notify the adapter

            myAdapter.notifyItemInserted(  messages.size() -1);//redraw the missing row
            binding.message.setText("");//remove what was typed
        });
        binding.theRecycleVIew.setAdapter(myAdapter = new RecyclerView.Adapter<RowHolder>()
        {



            @NonNull
            @Override
            public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//viewType is 0 or 1 based on getItemViewType(int position)
                if (viewType == 0){

                   SentRowBinding rowBinding = SentRowBinding.inflate(getLayoutInflater(), parent, false);
                //this will initialize the row variables:
                return new RowHolder(rowBinding.getRoot());

            }
                else{

                    ReceiveRowBinding rowBinding = ReceiveRowBinding.inflate(getLayoutInflater(), parent, false);

                    return new RowHolder(rowBinding.getRoot());

                }
            }



            @Override
            public void onBindViewHolder(@NonNull RowHolder holder, int position) {
//position is 0 or 1 based on getItemViewType(
                String message = messages.get(position);
                //override the text in the rows

                holder.message.setText(message);
                holder.time.setText("5:00pm");
            }
//tge number of items
            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){
//which layout to use for object at posistion
                String message=messages.get(position);
                if(position%2==0)
                {
                    return 0;
                }
                else
            return 1; //is position even

}
        });

        binding.theRecycleVIew.setLayoutManager(new LinearLayoutManager(this));

    }


    //this represents one row
class RowHolder extends  RecyclerView.ViewHolder{

        ArrayList<String> messages = new ArrayList<>();
   TextView message;
   TextView time;
    public RowHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.theMessage);
        time = itemView.findViewById(R.id.theTime);
    }
}


}
