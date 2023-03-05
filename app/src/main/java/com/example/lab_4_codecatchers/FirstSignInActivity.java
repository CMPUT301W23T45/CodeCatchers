package com.example.lab_4_codecatchers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_4_codecatchers.Player;
import com.example.lab_4_codecatchers.User;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Signs-in user for the first time
 *
 */
public class FirstSignInActivity extends AppCompatActivity {
    private FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();

    private Player currentUser = Player.getInstance();
    private EditText editUsername;
    private EditText editEmail;
    private EditText editPhone;
    private Button buttonStart;
    final String TAG = "Sample";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        buttonStart = findViewById(R.id.button_get_started);
        buttonStart.setOnClickListener(view -> reviewUser());
    }

    /**
     * Adds user to the database
     * @param newUser adds User object to db
     */
    protected void addUser(User newUser){
        fireStoreActivity.addUser(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"Data has been successfully added");
                        Intent activity = new Intent(FirstSignInActivity.this,MainActivity.class);
                        startActivity(activity);
                    }
                });


    }

    /**
     * Reviews if user entered sign-in form correctly
     * Creates a user object to add to database
     */
    protected void reviewUser(){
        editUsername = findViewById(R.id.editTextUsername);
        editEmail = findViewById(R.id.editTextEmailAddress);
        editPhone = findViewById(R.id.editTextPhoneNumber);
        String username = editUsername.getText().toString();
        String email = editEmail.getText().toString();
        String phone = editPhone.getText().toString();
        Log.d(TAG,"It works!");
        if(username.equals("")) {
            Toast.makeText(FirstSignInActivity.this, "Please enter username!", Toast.LENGTH_SHORT).show();
        } else{
            User newUser = new User();
            newUser.setUsername(username);
            newUser.getDevices().add(currentUser.getId());

            if(!email.equals("")){
                newUser.setEmail(email);
            }
            if(!phone.equals("")){
                newUser.setPhone(phone);
            }
            addUser(newUser);
        }
    }
}
