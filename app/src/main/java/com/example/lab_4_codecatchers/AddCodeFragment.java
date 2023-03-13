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
 * Fragment used to let a User add a new QR code to wallet
 * CameraFragment will jump to this fragment after a QR code is scanned
 * @see CameraFragment
 */
public class AddCodeFragment extends Fragment implements View.OnClickListener {
    User user;
    UserWallet userWallet;
    Code code; //code to add
    SwitchCompat geoSave;
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
        geoSave = view.findViewById(R.id.geoLocation);

        add.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }
    /**
     * Fill the information boxes for new QR code found in the AddCodeFragment xml
     * @param view current view
     */
    private void populateFields(View view) {
        TextView humanName = view.findViewById(R.id.addHumanName);
        TextView score = view.findViewById(R.id.addScore);
        // TODO: add photo view when implemented

        user = User.getInstance();
        userWallet = user.getCollectedQRCodes();
        code = userWallet.getCode((userWallet.getSize()) - 1);

        humanName.setText(code.getHumanName());
        score.setText(String.valueOf(code.getScore()));
    }

    /**
     * Defines behaviour when buttons are selected in AddCodeFragment
     * @param v current view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//           implemented with help from: Android Academics
//                URL: https://androidacademic.blogspot.com/2016/12/multiple-buttons-onclicklistener-android.html
//                Author: Pragnesh Ghoda
            case R.id.add_loc_photo:
                // TODO: jump to photo taking camera fragment
                break;
            case R.id.addButton:
                //The switches are returning a NullPointerException when using isChecked()
                //Commented out for now
                if(geoSave.isChecked()) {
                    code.setHumanName("Pam");
                    // TODO: add save location code here, then setCords of code
                }

                // TODO: add user comment to Code

                //update user in Firestore
                FireStoreActivity fireStore = FireStoreActivity.getInstance();
                fireStore.updateUser(user);

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