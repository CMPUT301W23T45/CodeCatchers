package com.example.lab_4_codecatchers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LeaderBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);


        // Find the "Camera" button in the layout
        Button cameraBtn = findViewById(R.id.camera_btn);

        // Set an OnClickListener for the "Camera" button
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to launch the MainActivity
                Intent intent = new Intent(LeaderBoardActivity.this, MainActivity.class);

                // Launch the MainActivity
                startActivity(intent);
            }
        });

        // Find the "Map" button in the layout
        Button mapButton = findViewById(R.id.map_btn);

        // Set an OnClickListener for the "Map" button
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to launch the MapActivity
                Intent intent = new Intent(LeaderBoardActivity.this, MapActivity.class);

                // Launch the MapActivity
                startActivity(intent);
            }
        });

        // Find the "Profile" button in the layout
        Button profileButton = findViewById(R.id.profile_btn);

        // Set an OnClickListener for the "Profile" button
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to launch the ProfileActivity
                Intent intent = new Intent(LeaderBoardActivity.this, ProfileActivity.class);

                // Launch the ProfileActivity
                startActivity(intent);
            }
        });


    }
}