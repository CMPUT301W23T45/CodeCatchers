package com.example.lab_4_codecatchers;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lab_4_codecatchers.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    User user;
    User currentUser = User.getInstance();

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //replaceFragment(new ProfileFragment()); // TODO: NEED to replace with camera or login fragment once impelmented

        if(!currentUser.locationAccepted){
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }else{
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

    }

    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            currentUser.setLocationAccepted(true);
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            Toast.makeText(this, "Please re-load app to grant precise location!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Please re-load app to grant precise location!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );



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
