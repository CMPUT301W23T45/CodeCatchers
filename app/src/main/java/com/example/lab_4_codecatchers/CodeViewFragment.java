package com.example.lab_4_codecatchers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CodeViewFragment extends Fragment implements View.OnClickListener {

    User user;
    UserWallet userWallet;
    QRList qrList;
    Code code; //code to add
    View view1;
    private RecyclerView recyclerView;

    public CodeViewFragment() {
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
        return inflater.inflate(R.layout.fragment_code_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view1 = view;
        user = User.getInstance();
        userWallet = user.getCollectedQRCodes();
        code = userWallet.getCurrentCode();

        populateFields(view);

        Button image = view.findViewById(R.id.viewImageLocation);
        Button delete = view.findViewById(R.id.deleteButton);
        ImageButton back = view.findViewById(R.id.backButton);
        LinearLayout coords = view.findViewById(R.id.coordLayout);
        LinearLayout comment = view.findViewById(R.id.commentLayout);

        image.setOnClickListener(this);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        coords.setOnClickListener(this);
        comment.setOnClickListener(this);

        // TODO: set the recyclerView and make the adapter
        ArrayList<String> players = getPlayers();

        recyclerView = view.findViewById(R.id.SameUserList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        SameQRCodeAdapter sameQRCodeAdapter = new SameQRCodeAdapter(getContext(), players);
        recyclerView.setAdapter(sameQRCodeAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    public ArrayList<String> getPlayers(){
        QRList list = QRList.getInstance();
        if(list.getSize() < 1) {
            return new ArrayList<String>();
        }else {
            int index = list.inList(code.getHash());
            MiniCode code1 = list.getCode(index);
            ArrayList<String> list1 = code1.getPlayersWhoScanned();
            if(list1.contains(user.getUsername())){
                list1.remove(user.getUsername());
            }
            return list1;
        }
    }

    private void populateFields(View view) {
        ImageView genImage = view.findViewById(R.id.genImage);
        TextView humanName = view.findViewById(R.id.codeHumanName);
        TextView coords = view.findViewById(R.id.coordView);
        TextView comment = view.findViewById(R.id.editComment);
        double latitude = code.getLocation().getLatitude();
        double longitude = code.getLocation().getLongitude();
        String locationString = latitude + ", " + longitude;

        humanName.setText(code.getHumanName());

        coords.setText(locationString);
        comment.setText(code.getComment());

        String loco = code.getQRImage();
        Picasso.get()
                .load(loco)
                .resize(130, 130)
                .centerCrop()
                .into(genImage);
    }

    /**
     * Defines behaviour when buttons are selected in AddCodeFragment
     * @param v current view
     */
    @Override
    public void onClick(View v) {
        FireStoreActivity fireStore = FireStoreActivity.getInstance();
        switch (v.getId()) {
            case R.id.viewImageLocation:
                new ViewPhotoFragment(code).show(getFragmentManager(), "ViewPhoto");
                break;

            case R.id.coordLayout:
                //goto map layout
                ((MainActivity) getActivity()).changeFragment(new MapFragment());
                break;

            case R.id.deleteButton:
                userWallet.removeCode(code);

                // update user in Firestore
                fireStore.updateUser(user);

                //update QRList
                qrList = QRList.getInstance();
                qrList.removeCode(code);

                //go back to playerWaller
                userWallet.setCurrentCode(null);
                ((MainActivity) getActivity()).changeFragment(new ProfileFragment());
                break;

            case R.id.backButton:
                //go back to playerWallet or Map
                if (userWallet.getBackToMap()) {
                    userWallet.setBackToMap(false);
                    ((MainActivity) getActivity()).changeFragment(new MapFragment());
                } else {
                    ((MainActivity) getActivity()).changeFragment(new ProfileFragment());
                }

                break;

            case R.id.commentLayout:
                new ChangeCommentFragment(code).show(getFragmentManager(), "Change Comment");
                break;

            default:
                break;
        }
    }


}