package com.example.execute_firebase_loginregister.ui.chats;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.execute_firebase_loginregister.R;
import com.example.execute_firebase_loginregister.model.ChatModel;
import com.example.execute_firebase_loginregister.model.UserModel;
import com.example.execute_firebase_loginregister.utlis.Constance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText message_et;
    private FloatingActionButton fab;
    List<ChatModel> modelList = new ArrayList<>();
    UserModel receiverModel;
    UserModel senderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        receiverModel  = (UserModel) getIntent().getSerializableExtra("user");
        initView();
    }

    private void initView() {

        recyclerView = findViewById(R.id.recyclerView_chat);
        message_et = findViewById(R.id.messageSend_et_chat);
        fab = findViewById(R.id.fab_send);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m = message_et.getText().toString();

                sendMessage(m);
            }
        });

        Constance.initRef().child("Chats").child(Constance.getUid(ChatActivity.this)).child(receiverModel.getuId()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                modelList.clear();

                for (DataSnapshot e : snapshot.getChildren())
                {
                    ChatModel m = e.getValue(ChatModel.class);
                    modelList.add(m);
                }

                recyclerView.setAdapter(new ChatAdapter(modelList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }


    private void sendMessage(final String m) {
        Constance.initRef().child("Users").child(Constance.getUid(ChatActivity.this)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                senderModel = snapshot.getValue(UserModel.class);

                ChatModel model = new ChatModel(m, senderModel.getName(), senderModel.getuId(), receiverModel.getName(), receiverModel.getuId());

                String key = Constance.initRef().child("Chats").child(Constance.getUid(ChatActivity.this)).child(receiverModel.getuId()).push().getKey();

                Constance.initRef().child("Chats").child(Constance.getUid(ChatActivity.this)).child(receiverModel.getuId()).child(key).setValue(model);
                Constance.initRef().child("Chats").child(receiverModel.getuId()).child(Constance.getUid(ChatActivity.this)).child(key).setValue(model);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    public class ChatAdapter extends RecyclerView.Adapter<ChatVH>{

        List<ChatModel> chatModels;

        public ChatAdapter(List<ChatModel> chatModels) {
            this.chatModels = chatModels;
        }

        @NonNull
        @Override
        public ChatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.chat_item, parent, false);
            return new ChatVH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatVH holder, int position) {
            ChatModel chatModel = chatModels.get(position);

            String message = chatModel.getMessage();
            String senderId = chatModel.getSenderUid();

            if(senderId.equals(Constance.getUid(ChatActivity.this)))
            {
                holder.linearLayout.setGravity(Gravity.END);
                holder.textView.setBackgroundColor(getResources().getColor(R.color.senderMessage));
            }

            holder.textView.setText(message);
        }

        @Override
        public int getItemCount() {
            return chatModels.size();
        }
    }

    public class ChatVH extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        TextView textView;

        public ChatVH(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearMessage_item);
            textView = itemView.findViewById(R.id.message_item);
        }
    }
}