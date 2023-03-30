package com.example.lab_4_codecatchers;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * ProfileFragment shows the users information like:
 *      list of scanned QR codes
 *      username
 *      total score
 *      highest and lowest QR
 *      ect.
 */
public class ProfileFragment extends Fragment implements ProfileAdapter.ItemClickListener {
    private User user;
    private ArrayList<Code> qrList;
    private UserWallet userWallet;
    private RecyclerView recyclerView;

    private FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();
    private ArrayList<User> allUsers = new ArrayList<>();

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUserInfo();
        setInfoBoxes(view);

        //setting recycler view to show list of scanned QRcodes
        recyclerView = view.findViewById(R.id.userQRList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ProfileAdapter profileAdapter = new ProfileAdapter(getContext(), qrList, this);
        recyclerView.setAdapter(profileAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);
        profileAdapter.notifyDataSetChanged();

    }

    /**
     * Fill the information boxes found in the profileFragment xml
     * @param view current view
     */
    private void setInfoBoxes(View view) {
        TextView highName = view.findViewById(R.id.humanNameHigh);
        TextView highScore = view.findViewById(R.id.qrScoreHigh);
        TextView lowName = view.findViewById(R.id.humanNameLow);
        TextView lowScore = view.findViewById(R.id.qrScoreLow);
        TextView sumOfScores = view.findViewById(R.id.scoreSum);
        TextView numQR = view.findViewById(R.id.numQR);
        TextView username = view.findViewById(R.id.username);
        TextView totalPoints = view.findViewById(R.id.totalPoints);
        TextView rank = view.findViewById(R.id.currentRank);

        sumOfScores.setText(String.valueOf(userWallet.getTotal()));
        numQR.setText(String.valueOf(userWallet.getSize()));
        username.setText(user.getUsername());

        if (userWallet.getSize() == 0) {
            highName.setText(" ");
            highScore.setText(" ");
            lowName.setText(" ");
            lowScore.setText(" ");
        } else {
            Code highCode = userWallet.getHighest();
            Code lowCode = userWallet.getLowest();
            highName.setText(highCode.getHumanName());
            highScore.setText(String.valueOf(highCode.getScore()));
            lowName.setText(lowCode.getHumanName());
            lowScore.setText(String.valueOf(lowCode.getScore()));
            rank.setText(String.valueOf(user.getRank()));
            allUsers.clear();
            fireStoreActivity.isUniqueUsername()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        queryDocumentSnapshots.getDocuments().forEach(documentSnapshot -> allUsers.add(documentSnapshot.toObject(User.class)));
                        int rankUniqueCode = rankByUniqueScore(allUsers);
                        totalPoints.setText(" "+rankUniqueCode);
                    });
        }

    }

    /**
     * Gets current user, current user's wallet and list of current user's scanned codes
     */
    private void setUserInfo() {
        user = User.getInstance();
        userWallet = user.getCollectedQRCodes();
        qrList = userWallet.getUserCodes();
    }

    @Override
    public void onItemClick(Code code) {
        userWallet.setCurrentCode(code);
        ((MainActivity) getActivity()).changeFragment(new CodeViewFragment());

    }
    private int rankByUniqueScore(ArrayList<User> users){
        users.sort((user,i)-> i.getHighestUniqueCode() - user.getHighestUniqueCode());
        for(int i =0;i < users.size();i++){
            if(users.get(i).getUsername().equals(user.getUsername())){
                return i+1;
            }
        }
        return 0;
    }

}