package com.example.lab_4_codecatchers;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtherPlayer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtherPlayer extends Fragment implements ProfileAdapter.ItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERNAME = "username";


    // TODO: Rename and change types of parameters
    private String username;
    User user;
    UserWallet userWallet;
    FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();
    private ArrayList<User> allUsers = new ArrayList<>();
    RecyclerView recyclerView;



    public OtherPlayer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username Parameter 1.

     * @return A new instance of fragment OtherPlayer.
     */
    // TODO: Rename and change types and number of parameters
    public static OtherPlayer newInstance(String username) {
        OtherPlayer fragment = new OtherPlayer();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFromfirestore();
        setInfoBoxes(view);

        //setting recycler view to show list of scanned QRcodes
        recyclerView = view.findViewById(R.id.userQRList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ProfileAdapter profileAdapter = new ProfileAdapter(getContext(), userWallet.getUserCodes(), this);
        recyclerView.setAdapter(profileAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);
        profileAdapter.notifyDataSetChanged();

    }

    public void getFromfirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Users");
        UserWallet userWallet;

        // Extract from intent
       // Intent intent = this.getIntent();
        //Bundle extras = intent.getExtras();
        //if (extras != null) {
          //  username = (String) extras.get("username");
            //playerHash = (String) extras.get("playerHash");


        // Extract Player from database
        DocumentReference playerDocRef = db.collection("Users").document(username);

        playerDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        user = documentSnapshot.toObject(User.class);


                        //ProfileAdapter.notifyDataSetChanged();

                        Log.d("Success", "12");
                        // display to list view

                    }
                }
            }
        });
        userWallet = user.getCollectedQRCodes();
    }



    /**
     * Sorts the users in terms of the highest QR code scores
     * @param users
     * @return the rank of the user
     */
    private int rankByUniqueScore(ArrayList<User> users){
        users.sort((user,i)-> i.getHighestUniqueCode() - user.getHighestUniqueCode());
        for(int i =0;i < users.size();i++){
            if(users.get(i).getUsername().equals(user.getUsername())){
                return i+1;
            }
        }
        return 0;
    }

    /**
     * Fill the information boxes found in the profileFragment xml
     * @param view current view
     */
    private void setInfoBoxes(View view) {
        TextView highName = view.findViewById(R.id.humanNameHigh);
//        TextView highScore = view.findViewById(R.id.qrScoreHigh);
        TextView lowName = view.findViewById(R.id.humanNameLow);
//        TextView lowScore = view.findViewById(R.id.qrScoreLow);
        TextView sumOfScores = view.findViewById(R.id.scoreSum);
        TextView numQR = view.findViewById(R.id.numQR);
        TextView username = view.findViewById(R.id.username);
        TextView totalPoints = view.findViewById(R.id.totalPoints);
        TextView email = view.findViewById(R.id.userEditEmail);
        TextView phone = view.findViewById(R.id.userEditPhone);

        sumOfScores.setText(String.valueOf(userWallet.getTotal()));
        numQR.setText(String.valueOf(userWallet.getSize()));
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());


        if (userWallet.getSize() == 0) {
            highName.setText(" ");
//            highScore.setText(" ");
            lowName.setText(" ");
//            lowScore.setText(" ");
        } else {
            Code highCode = userWallet.getHighest();
            Code lowCode = userWallet.getLowest();
            highName.setText(highCode.getHumanName());
//            highScore.setText(String.valueOf(highCode.getScore()));
            lowName.setText(lowCode.getHumanName());
//            lowScore.setText(String.valueOf(lowCode.getScore()));
            allUsers.clear();
            fireStoreActivity.isUniqueUsername()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        queryDocumentSnapshots.getDocuments().forEach(documentSnapshot -> allUsers.add(documentSnapshot.toObject(User.class)));
                        int rankUniqueCode = rankByUniqueScore(allUsers);
                        totalPoints.setText(" "+rankUniqueCode);
                    });
        }

    }

    @Override
    public void onItemClick(Code code) {
        return ;

    }

}