package com.example.lab_4_codecatchers;

import static android.content.ContentValues.TAG;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class OtherPlayer extends Fragment implements ProfileAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private User user = new User();
    private UserWallet userWallet = new UserWallet();
    private ArrayList<Code> qrList = new ArrayList<>();
    FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();
    private ArrayList<User> allUsers = new ArrayList<>();
    RecyclerView recyclerView;


    public OtherPlayer() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        getFromFireStore(view);
        //setting recycler view to show list of scanned QRcodes
        recyclerView = view.findViewById(R.id.userQRList2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ProfileAdapter profileAdapter = new ProfileAdapter(getContext(), qrList, this);
        recyclerView.setAdapter(profileAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);
        profileAdapter.notifyDataSetChanged();
    }

    /**
     * Retrieves the user from the database
     * @param view
     */

    public void getFromFireStore(View view) {
        String username = getArguments().getString("username");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Extract Player from database
        DocumentReference playerDocRef = db.collection("Users").document(username);

        playerDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        user = documentSnapshot.toObject(User.class);

                        Log.d(TAG,"user exists"+ user.getCollectedQRCodes());
                        userWallet = user.getCollectedQRCodes();
                        Log.d(TAG,"user size"+ userWallet.getSize());
                        int size = userWallet.getSize();
                        for(int i = 0; i < size; i++){
                            Code code = userWallet.getCode(i);
                            qrList.add(code);
                        }

                        TextView username2 = view.findViewById(R.id.username2);
                        TextView highName = view.findViewById(R.id.humanNameHigh2);
                        TextView lowName = view.findViewById(R.id.humanNameLow2);
                        TextView sumOfScores = view.findViewById(R.id.scoreSum2);
                        TextView numQR = view.findViewById(R.id.numQR2);
                        TextView totalPoints = view.findViewById(R.id.totalPoints2);
                        TextView email = view.findViewById(R.id.userEditEmail2);
                        TextView phone = view.findViewById(R.id.userEditPhone2);

                        sumOfScores.setText(String.valueOf(userWallet.getTotal()));
                        numQR.setText(String.valueOf(userWallet.getSize()));
                        email.setText(user.getEmail());
                        phone.setText(user.getPhone());
                        username2.setText(user.getUsername());


                        if (userWallet.getSize() == 0) {
                            highName.setText(" ");
                            lowName.setText(" ");
                        } else {
                            Code highCode = userWallet.getHighest();
                            Code lowCode = userWallet.getLowest();
                            highName.setText(highCode.getHumanName());
                            lowName.setText(lowCode.getHumanName());
                            allUsers.clear();
                            fireStoreActivity.isUniqueUsername()
                                    .addOnSuccessListener(queryDocumentSnapshots -> {
                                        queryDocumentSnapshots.getDocuments().forEach(documentSnapshot2 -> allUsers.add(documentSnapshot2.toObject(User.class)));
                                        int rankUniqueCode = rankByUniqueScore(allUsers);
                                        totalPoints.setText(" "+rankUniqueCode);
                                    });
                        }

                    }
                }
            }
        });
    }


    /**
     * Sorts the users in terms of the highest QR code scores
     *
     * @param users
     * @return the rank of the user
     */
    private int rankByUniqueScore(ArrayList<User> users) {
        users.sort((user, i) -> i.getHighestUniqueCode() - user.getHighestUniqueCode());
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                return i + 1;
            }
        }
        return 0;
    }


    @Override
    public void onItemClick(Code code) {
        return;

    }

    /**
     * Gets the players' scans from database
     */


}