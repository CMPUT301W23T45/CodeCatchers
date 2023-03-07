package com.example.lab_4_codecatchers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;

public class FirstSignInActivity extends AppCompatActivity {private FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();

    private User currentUser = User.getInstance();
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
            currentUser.setUsername(username);

            if(!email.equals("")){
                newUser.setEmail(email);
                currentUser.setEmail(email);
            }
            if(!phone.equals("")){
                newUser.setPhone(phone);
                currentUser.setPhone(phone);
            }
            addUser(newUser);
        }
    }


}
