package com.example.lab_4_codecatchers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This class is responsible for displaying a list of all users playing the game.
 */
public class OtherPlayers extends AppCompatActivity {
    // Initialize variables
    private SingletonPlayer singletonPlayer;
    private ArrayList<User> allPlayers;
    private FirebaseFirestore db;
    private ArrayAdapter<User> playerAdapter;
    private ListView playerList;
    private Button scanCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_other_users);

        // fetch all the document to display them on listview
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users");

        // Prepare ListView and Adapter
        playerList = findViewById(R.id.userQRList);
        allPlayers = new ArrayList<>();
        playerAdapter = new OtherPlayerListAdapter(this, R.layout.fragment_other_users, allPlayers);
        playerList.setAdapter(playerAdapter);

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                allPlayers.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    User p = doc.toObject(User.class);
                    if (!p.getCollectedQRCodes().getCurrentCode().getHash().equals(singletonPlayer.player.getCollectedQRCodes().getCurrentCode().getHash()) && !p.getOwner()) {
                        allPlayers.add(p);
                    }

                }
                //OtherPlayerListAdapter.notifyDataSetChanged();
            }
        });

        // Be able to view a profile if we click a user
        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               User clickedPLayer = allPlayers.get(position);
                String playerUserName = clickedPLayer.getUsername();
                //String playerHash = clickedPLayer.getCollectedQRCodes().getCurrentCode().getHash();
                Intent intent = new Intent(OtherPlayers.this, OtherPlayerProfile.class);
                intent.putExtra("playerUserName", playerUserName);
                //intent.putExtra("playerHash", playerHash);
                startActivity(intent);
            }
        });
    }
}

