package com.example.lab_4_codecatchers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private User user;
    private ArrayList<Code> qrList;
    private UserWallet userWallet;
    private RecyclerView recyclerView;

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

        recyclerView = view.findViewById(R.id.userQRList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ProfileAdapter profileAdapter = new ProfileAdapter(getContext(), qrList);
        recyclerView.setAdapter(profileAdapter);
        profileAdapter.notifyDataSetChanged();
    }

    private void setInfoBoxes(View view) {
        TextView highestScore = view.findViewById(R.id.highQR);
        TextView lowestScore = view.findViewById(R.id.lowQR);
        TextView sumOfScores = view.findViewById(R.id.scoreSum);
        TextView numQR = view.findViewById(R.id.numQR);
        TextView username = view.findViewById(R.id.username);
        TextView totalPoints = view.findViewById(R.id.totalPoints);
        TextView rank = view.findViewById(R.id.currentRank);

        highestScore.setText(String.valueOf(userWallet.getHighest()));
        lowestScore.setText(String.valueOf(userWallet.getLowest()));
        sumOfScores.setText(String.valueOf(userWallet.getTotal()));
        numQR.setText(String.valueOf(userWallet.getSize()));
        username.setText(user.getUsername());
        totalPoints.setText(String.valueOf(userWallet.getTotal()));
        rank.setText(String.valueOf(user.getRank()));

    }

    private void setUserInfo() {
        user = ((MainActivity) requireActivity()).getUser();
        userWallet = user.getCollectedQRCodes();
        qrList = userWallet.getUserCodes();
    }
}