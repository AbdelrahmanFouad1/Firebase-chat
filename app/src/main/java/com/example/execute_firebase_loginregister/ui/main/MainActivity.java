  package com.example.execute_firebase_loginregister.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.execute_firebase_loginregister.R;
import com.example.execute_firebase_loginregister.ui.register.RegisterFragment;

  public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment();
    }

      private void loadFragment() {
          getSupportFragmentManager()
                  .beginTransaction()
                  .add(R.id.frame_container, new RegisterFragment())
                  .disallowAddToBackStack()
                  .commit();
      }
  }