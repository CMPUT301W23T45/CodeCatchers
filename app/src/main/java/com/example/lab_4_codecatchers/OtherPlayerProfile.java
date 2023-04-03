package com.example.lab_4_codecatchers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.ArrayList;
/**
 * This class is responsible for displaying other player information.
 */
public class OtherPlayerProfile extends AppCompatActivity {
    private String playerUserName;
    private String playerHash;
    private FirebaseFirestore db;
    private User player;
    //private ArrayAdapter<QRCode> scanAdapter;
    private ArrayList<Code> qrCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_other_users);

        ListView qrCodeListView = findViewById(R.id.userQRList);
        // Create list adapter
        qrCodes = new ArrayList<>();
        //scanAdapter = new ScanListAdapter(this, R.layout.my_scans_adapter, qrCodes);
        //qrCodeListView.setAdapter(scanAdapter);


        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Users");

        // Extract from intent
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            playerUserName = (String) extras.get("playerUserName");
            playerHash = (String) extras.get("playerHash");
        }

        // Extract Player from database
        DocumentReference playerDocRef = db.collection("Users").document(playerUserName);
        playerDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        player = documentSnapshot.toObject(User.class);
                        qrCodes.clear();
                        for (int i = 0; i < player.getCollectedQRCodes().getSize(); i++) {
                            qrCodes.add(player.getCollectedQRCodes().getCode(i));
                        }
                        //ProfileAdapter.notifyDataSetChanged();
                        Log.d("Success", "12");
                        // display to list view
                        displayPlayerInfo();
                    }
                }
            }
        });
        qrCodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // go to viewQRcode activity
                Code code = qrCodes.get(i);
                Intent intent = new Intent(getApplicationContext(), Code.class);
                intent.putExtra("qrcode_info2", (Parcelable) code);
                intent.putExtra("otherPlayerName", playerUserName);
                intent.putExtra("isOtherPlayer", "true");  // let viewQRcode know this is from otherplayer.
                startActivity(intent);

            }
        });

    }

    /**
     * This method displays the other players information.
     */
    public void displayPlayerInfo() {
        //TextView userNameTextView = findViewById(R.id.OtherPlayerProfileName);
        TextView username = findViewById(R.id.username);
        username.setText("username: " + playerUserName);

        //TextView totalScoreTextView = findViewById(R.id.OtherPlayerProfileScore);
        TextView totalPoints = findViewById(R.id.totalPoints);
        totalPoints.setText("total points: " + player.getTotalScore());

        //TextView emailTextView = findViewById(R.id.OtherPlayerProfileEmail);
        //emailTextView.setText("email: " + player.getEmail());

        TextView rank = findViewById(R.id.currentRank);
        rank.setText("rank: " + player.getRank());


    }


}
