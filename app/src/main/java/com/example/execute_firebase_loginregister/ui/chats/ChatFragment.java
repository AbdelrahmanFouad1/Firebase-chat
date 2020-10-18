package com.example.execute_firebase_loginregister.ui.chats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.execute_firebase_loginregister.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class ChatFragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private EditText message_et;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
//
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
//        Serializable getText = getArguments().getSerializable("user");
        recyclerView = view.findViewById(R.id.recyclerView_chat);
        message_et = view.findViewById(R.id.messageSend_et_chat);
        fab = view.findViewById(R.id.fab_send);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
