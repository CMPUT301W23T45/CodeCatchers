package com.example.lab_4_codecatchers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.lab_4_codecatchers.databinding.ActivityMainBinding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    User user;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //replaceFragment(new ProfileFragment()); // TODO: NEED to replace with camera or login fragment once impelmented

        //get data from firebase
        populatedUser();

        binding.navBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.camera:
                    replaceFragment(new CameraFragment());
                    break;
                case R.id.map:
                    replaceFragment(new MapFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.leaderBoard:
                    replaceFragment(new LeaderBoardFragment());
                    break;
            }

            return true;
        });

        // Testing hash function
        // TODO: Remove
        hash("test");
    }

    private void populatedUser() {
        // TODO: add the firebase stuff here (I just manually created a User for testing)
        ArrayList<Code> qrList = new ArrayList<Code>();
        qrList.add(new Code(150, 0, "Jimmy", 0));
        qrList.add(new Code(8000, 0, "Linda", 0));
        qrList.add(new Code(7803, 0, "Hose", 0));
        user = new User("273869", "user_1234", "123@gmail.com", "780-123-4560", 15953, 1, qrList);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    public User getUser() {
        return user;
    }

    // Returns the SHA-256 hash for any given string
    // Based on code from http://www.java2s.com/example/android/java.lang/sha256-hash-string.html
    public static String hash(String s) {
        byte[] rawHash = null;
        String output = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            rawHash = digest.digest(s.getBytes());
        } catch (NoSuchAlgorithmException e) {
            Log.e("CodeCatchers", "Cannot calculate SHA-256");
        }
        if (rawHash != null) {
            StringBuilder sb = new StringBuilder();
            for (byte b: rawHash) {
                sb.append(String.format("%02x", b));
            }
            output = sb.toString();
        }
        return output;
    }
}
