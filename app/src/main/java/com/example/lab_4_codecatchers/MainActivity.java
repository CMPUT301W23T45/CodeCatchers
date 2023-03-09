package com.example.lab_4_codecatchers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.lab_4_codecatchers.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    User user;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ProfileFragment()); // TODO: NEED to replace with camera or login fragment once impelmented

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
}
