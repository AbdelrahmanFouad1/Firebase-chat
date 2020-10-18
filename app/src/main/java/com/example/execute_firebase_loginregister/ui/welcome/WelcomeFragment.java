package com.example.execute_firebase_loginregister.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.execute_firebase_loginregister.R;
import com.example.execute_firebase_loginregister.model.UserModel;
import com.example.execute_firebase_loginregister.ui.chats.ChatActivity;
import com.example.execute_firebase_loginregister.ui.chats.ChatFragment;
import com.example.execute_firebase_loginregister.ui.register.RegisterFragment;
import com.example.execute_firebase_loginregister.utlis.Constance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WelcomeFragment extends Fragment {
    View view;
    Button buttonOut;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    List<UserModel> userModel = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_welcome, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.rv);
        progressBar = view.findViewById(R.id.progress);

        buttonOut = view.findViewById(R.id.out);
        buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Constance.replaceFragmentAndNotBack(WelcomeFragment.this, new RegisterFragment());
            }
        });

        Constance.initRef().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModel.clear();
                for (DataSnapshot e : snapshot.getChildren()) {

                    UserModel u = e.getValue(UserModel.class);

                    if (!u.getuId().equals(Constance.getUid(requireActivity())))
                        userModel.add(u);
                }
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(new UserAdapter(userModel));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public class UserAdapter extends RecyclerView.Adapter<UserVH> {
        List<UserModel> userModels;

        public UserAdapter(List<UserModel> userModels) {
            this.userModels = userModels;
        }

        @NonNull
        @Override
        public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View viewAdapter = LayoutInflater.from(getActivity()).inflate(R.layout.user_item, parent, false);
            return new UserVH(viewAdapter);
        }

        @Override
        public void onBindViewHolder(@NonNull UserVH holder, int position) {
            final UserModel u = userModels.get(position);

            holder.nameField.setText(u.getName());
            holder.phoneField.setText(u.getPhone());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ChatFragment fragment = new ChatFragment();
//                    Bundle args = new Bundle();
//                    args.putSerializable("user", u);
//                    fragment.setArguments(args);

//                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("user", u);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return userModels.size();
        }
    }

//    private void replaceFragments() {
//                            requireActivity().getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(WelcomeFragment.this, new ChatFragment())
//                            .commit();
//    }

    public class UserVH extends RecyclerView.ViewHolder {

        TextView nameField, phoneField;

        public UserVH(@NonNull View itemView) {
            super(itemView);
            nameField = itemView.findViewById(R.id.name_txt);
            phoneField = itemView.findViewById(R.id.phone_txt);
        }
    }
}