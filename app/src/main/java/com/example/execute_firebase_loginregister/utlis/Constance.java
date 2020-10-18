package com.example.execute_firebase_loginregister.utlis;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.example.execute_firebase_loginregister.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Constance {

    private static DatabaseReference databaseReference;

    private static SharedPreferences sharedPreferences;

    public static void replaceFragment(Fragment from, Fragment to) {
        from
                .requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, to)
                .addToBackStack(null)
                .commit();
    }

    public static void replaceFragmentAndNotBack(Fragment from, Fragment to) {
        from
                .requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, to)
                .disallowAddToBackStack()
                .commit();
    }

    public static void saveUid(Activity activity, String id){

        sharedPreferences = activity.getSharedPreferences("SOCIAL", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Uid", id);
        editor.apply();
    }

    public static String getUid(Activity activity){

        sharedPreferences = activity.getSharedPreferences("SOCIAL", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Uid", "empty");
    }

    public static DatabaseReference initRef(){
        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

}
