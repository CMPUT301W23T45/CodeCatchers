package com.example.lab_4_codecatchers;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    User user;
    User currentUser = User.getInstance();

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //replaceFragment(new ProfileFragment()); // TODO: NEED to replace with camera or login fragment once impelmented

        if(!currentUser.isLocationAccepted()){
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
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
//       }
        // end

        //get data from firebase
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

    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            currentUser.setLocationAccepted(true);
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            Toast.makeText(this, "Grant Precise Location!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Grant Location!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
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
