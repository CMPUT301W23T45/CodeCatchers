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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.example.lab_4_codecatchers.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    User user;
    private GoogleMap map;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //replaceFragment(new ProfileFragment()); // TODO: NEED to replace with camera or login fragment once impelmented
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, CAMERA_PERMISSION_REQUEST_CODE);

        replaceFragment(new CameraFragment());
        binding.navBar.getMenu().getItem(1).setChecked(true);
        //get data from firebase
        user = User.getInstance();
        populatedUser();

        binding.navBar.setOnItemSelectedListener(item -> {
//            NavBar implemented with assistance from: Foxandroid on YouTube
//                         URL: https://www.youtube.com/watch?v=Bb8SgfI4Cm4
//                         Author: Foxandroid
            switch (item.getItemId()) {
                case R.id.camera: // this case too
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        replaceFragment(new CameraFragment());
                    } else {
                        Toast.makeText(this, "Camera permission not granted", Toast.LENGTH_SHORT).show();
                    }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            boolean cameraPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean locationPermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

            if (cameraPermissionGranted && locationPermissionGranted) {
                replaceFragment(new CameraFragment());
            } else {
                Toast.makeText(this, "Camera and location permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * Tester function to populate a user's wallet
     */
    private void populatedUser() {
        UserWallet qrList = user.getCollectedQRCodes();
        String testImage = "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";
        qrList.addCode(new Code(150, "0", "Jimmy", "0","", ""));
        qrList.addCode(new Code(8000, "1", "Linda", "0","", ""));
        qrList.addCode(new Code(7803, "2", "Betty", "0", "Thats one silly dino!", ""));
        qrList.getCode(2).setImageString(testImage);
        //qrList.addCode(new Code(5234, "6e80a5bf1f6e165f65965076290a61638dfde0f2972474d73b954a10962a392f"));
        //user = new User("273869", "user_1234", "123@gmail.com", "780-123-4560", 15953, 1, qrList);
    }

    /**
     * replaces the fragment currently in the frameLayout
     * @param fragment desired fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    /**
     * replaces the fragment currently in the frameLayout
     * (public version that just calls replaceFragment)
     * @param fragment desired fragment
     */
    public void changeFragment(Fragment fragment) {
        replaceFragment(fragment);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
    }
}
