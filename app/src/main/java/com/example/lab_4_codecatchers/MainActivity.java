package com.example.lab_4_codecatchers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;


import com.example.lab_4_codecatchers.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    User user;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // start
        // Check if camera permission has been granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
//        else {
//            // Permission has already been granted
//            replaceFragment(new CameraFragment());
//            binding.navBar.getMenu().getItem(1).setChecked(true);
//        }
        // end
        //get data from firebase
        replaceFragment(new CameraFragment());
        binding.navBar.getMenu().getItem(1).setChecked(true);
        //get data from firebase
        user = User.getInstance();
        populatedUser();

        binding.navBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.camera: // this case too
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        replaceFragment(new CameraFragment());
                    } else {
                        Toast.makeText(this, "Camera permission not granted", Toast.LENGTH_SHORT).show();
                    }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //camera permission granted
                //replaceFragment(new CameraFragment());
            } else {
                //camera permission denied
                //handle this case, e.g. show an error message
            }
        }
    }

    private void populatedUser() {
        UserWallet qrList = user.getCollectedQRCodes();
        qrList.addCode(new Code(150, 0, "Jimmy", 0));
        qrList.addCode(new Code(8000, 0, "Linda", 0));
        qrList.addCode(new Code(7803, 0, "Betty", 0));
        //user = new User("273869", "user_1234", "123@gmail.com", "780-123-4560", 15953, 1, qrList);
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
