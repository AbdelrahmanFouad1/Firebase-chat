package com.example.execute_firebase_loginregister.ui.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.execute_firebase_loginregister.R;
import com.example.execute_firebase_loginregister.model.UserModel;
import com.example.execute_firebase_loginregister.ui.login.LoginFragment;
import com.example.execute_firebase_loginregister.ui.welcome.WelcomeFragment;
import com.example.execute_firebase_loginregister.utlis.Constance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RegisterFragment extends Fragment {
    View view;
    EditText name, email, password, confirmPassword, phone, code;
    Button register, go;
    TextView replace;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
//    String hhh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        if (auth.getCurrentUser() != null){
            Constance.replaceFragment(RegisterFragment.this, new WelcomeFragment());
        }

        name = view.findViewById(R.id.name_et_register);
        email = view.findViewById(R.id.email_et_register);
        password = view.findViewById(R.id.password_et_register);
        confirmPassword = view.findViewById(R.id.confirmPassword_et_register);
        phone = view.findViewById(R.id.phone_et_register);
        register = view.findViewById(R.id.register_btn_register);
//        code = view.findViewById(R.id.code_et_register);

        replace = view.findViewById(R.id.replace_tv_register);
//        go = view.findViewById(R.id.go_btn_register);

//        go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                verifyPhoneNumberWithCode(hhh, code.getText().toString());
//            }
//        });

        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                requireActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frame_container, new LoginFragment())
//                        .addToBackStack(null)
//                        .commit();
                Constance.replaceFragment(RegisterFragment.this, new LoginFragment());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = name.getText().toString();
                String emails = email.getText().toString();
                String pass = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                String phones = phone.getText().toString();

                if (names.isEmpty() || emails.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || phones.isEmpty()) {
                    Toast.makeText(requireActivity(), "invalid data", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(confirmPass)) {
                    Toast.makeText(requireActivity(), "password is't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();
                authWithEmail(names, emails, pass, phones);
//                authWithPhone(phones);
            }
        });

//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential credential) {
//
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                hhh = verificationId;
//            }
//        };
        // [END phone_auth_callbacks]

    }


//    private void authWithPhone(String phones) {
////        progressDialog.dismiss();
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+2" + phones,        // Phone number to verify
//                10,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                Objects.requireNonNull(getActivity()),               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//
//    }

//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
////                progressDialog.dismiss();
//                if (task.isSuccessful()) {
//                    Toast.makeText(getActivity(), task.getResult().getUser().getUid(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }


//    private void verifyPhoneNumberWithCode(String verificationId, String code) {
//
//
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        signInWithPhoneAuthCredential(credential);
//    }

    private void authWithEmail(final String names, final String emails, final String pass, final String phones) {

        auth.createUserWithEmailAndPassword(emails, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    task.getResult().getUser().sendEmailVerification();
                    creatUser(names, emails, pass, phones, task.getResult().getUser().getUid());
                } else {
                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void creatUser(String names, String emails, String pass, String phones, final String uid) {

        UserModel userModel = new UserModel(uid, names, emails, pass, phones);
        databaseReference.child("Users").child(uid).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
//                    Constance.replaceFragmentAndNotBack(RegisterFragment.this, new WelcomeFragment());
                    Constance.saveUid(getActivity(), uid);
                } else {
                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
