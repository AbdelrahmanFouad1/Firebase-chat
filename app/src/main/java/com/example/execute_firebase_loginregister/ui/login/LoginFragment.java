package com.example.execute_firebase_loginregister.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.execute_firebase_loginregister.R;
import com.example.execute_firebase_loginregister.ui.welcome.WelcomeFragment;
import com.example.execute_firebase_loginregister.utlis.Constance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    View view;
    EditText emailField, passwordField;
    Button loginBtn;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        auth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait!");
        progressDialog.setCancelable(false);

        emailField = view.findViewById(R.id.email_et_login);
        passwordField = view.findViewById(R.id.password_et_login);
        loginBtn = view.findViewById(R.id.register_btn_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emails = emailField.getText().toString();
                String passwords = passwordField.getText().toString();

                if (emails.isEmpty() || passwords.isEmpty()) {
                    Toast.makeText(requireActivity(), "invalid data ", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();
                authWithEmail(emails, passwords);

            }
        });
    }


    private void authWithEmail(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    if (task.getResult().getUser().isEmailVerified()){
                        Constance.saveUid(getActivity(), task.getResult().getUser().getUid());
                        Toast.makeText(getActivity(), task.getResult().getUser().getUid(), Toast.LENGTH_SHORT).show();
                        Constance.replaceFragmentAndNotBack(LoginFragment.this, new WelcomeFragment());

                    }else {
                        Toast.makeText(getActivity(), "Please verify your email", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(requireActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
