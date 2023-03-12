package com.example.lab_4_codecatchers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCodeFragment extends Fragment implements View.OnClickListener {
    UserWallet userWallet;
    Code code; //code to add
    public AddCodeFragment() {
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
        return inflater.inflate(R.layout.fragment_add_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateFields(view);

        Button add = view.findViewById(R.id.addButton);
        Button cancel = view.findViewById(R.id.cancelButton);

        add.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    private void populateFields(View view) {
        TextView humanName = view.findViewById(R.id.addHumanName);
        TextView score = view.findViewById(R.id.addScore);
        // TODO: add photo view when implemented

        userWallet = User.getInstance().getCollectedQRCodes();
        code = userWallet.getCode((userWallet.getSize()) - 1);

        humanName.setText(code.getHumanName());
        score.setText(String.valueOf(code.getScore()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                //The switches are returning a NullPointerException when using isChecked()
                //Commented out for now
//                SwitchCompat geoSwitch = (SwitchCompat) v.findViewById(R.id.geoLocation);
//                SwitchCompat photoSwitch = (SwitchCompat) v.findViewById(R.id.addPhoto);
//                if(geoSwitch.isChecked()) {
//                    // TODO: add save location code here, then setCords of code
//                }
//
//                if(photoSwitch.isChecked()) {
//                    // TODO: jump to photo taking camera fragment
//                }
//
//                // TODO: add user comment to Code
                break;

            case R.id.cancelButton:
                //remove Code from wallet
                userWallet.removeCode(code);
                break;
            default:
                break;
        }
        //jump back to camera fragment
        ((MainActivity) getActivity()).changeFragment(new CameraFragment());
    }
}