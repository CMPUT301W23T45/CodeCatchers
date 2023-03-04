package com.example.codecatchers;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    Player currentUser = Player.getInstance();
    FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
    }

    @Override
    protected void onStart(){
        super.onStart();
        deviceCheck();
    }

    /**
     * Checks to see if this device has already logged in
     * If user exists then sets the current user to data retrieved -> so it can be used all throughout the program
     */
    protected void deviceCheck(){
        currentUser.setId(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        fireStoreActivity.getUsersBasedOnDevice()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> usersUsingDevice = queryDocumentSnapshots.getDocuments();
                    if(usersUsingDevice.isEmpty()){
                        Log.d(TAG,"No User Exists!");
                        noUserExists();
                    }else{
                        //Since user exists get info of the player and set the currentUser playing to information retrieved
                        User current = usersUsingDevice.get(0).toObject(User.class);
                        assert current != null;
                        currentUser.setUsername(current.getUsername());
                        currentUser.setEmail(current.getEmail());
                        currentUser.setPhone(current.getPhone());
                        Log.d(TAG,"User Exists!");
                        userExists();
                    }
                });
    }

    /**
     * If user exists then direct to MainActivity
     */
    public void userExists(){
        Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * If user does not exist then direct to FirstTimeActivity
     */
    public void noUserExists(){
        Intent intent = new Intent(SplashScreenActivity.this,FirstSignInActivity.class);
        startActivity(intent);
        finish();
    }


}
