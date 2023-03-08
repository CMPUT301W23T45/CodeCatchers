package com.example.lab_4_codecatchers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find the "Leader" button in the layout
        Button leaderboardButton = findViewById(R.id.leaderboard_btn);

        // Set an OnClickListener for the "Leader" button
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to launch the LeaderBoardActivity
                Intent intent = new Intent(MainActivity.this, LeaderBoardActivity.class);

                // Launch the LeaderBoardActivity
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
                Intent intent = new Intent(MainActivity.this, MapActivity.class);

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
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);

                // Launch the ProfileActivity
                startActivity(intent);
            }
        });
    }
}