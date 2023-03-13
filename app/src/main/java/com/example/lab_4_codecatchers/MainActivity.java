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

    private void populatedUser() {
        UserWallet qrList = user.getCollectedQRCodes();
        qrList.addCode(new Code(150, null, "Jimmy", 0));
        qrList.addCode(new Code(8000, null, "Linda", 0));
        qrList.addCode(new Code(7803, null, "Betty", 0));
        //user = new User("273869", "user_1234", "123@gmail.com", "780-123-4560", 15953, 1, qrList);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    public void changeFragment(Fragment fragment) {
        replaceFragment(fragment);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
    }
}