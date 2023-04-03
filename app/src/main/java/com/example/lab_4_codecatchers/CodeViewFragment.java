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
 * Allows players to view their scanned QRcodes
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
        //update QRList
        FireStoreActivity.getInstance().fillQRList();


        view1 = view;
        user = User.getInstance();
        userWallet = user.getCollectedQRCodes();
        code = userWallet.getCurrentCode();

        populateFields(view);

        Button image = view.findViewById(R.id.viewImageLocation);
        Button delete = view.findViewById(R.id.deleteButton);
        ImageButton back = view.findViewById(R.id.backButton);
        TextView coords = view.findViewById(R.id.coordView);
        LinearLayout comment = view.findViewById(R.id.commentLayout);

        image.setOnClickListener(this);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        coords.setOnClickListener(this);
        comment.setOnClickListener(this);

        ArrayList<String> players = getPlayers();

        recyclerView = view.findViewById(R.id.SameUserList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        SameQRCodeAdapter sameQRCodeAdapter = new SameQRCodeAdapter(getContext(), players);
        recyclerView.setAdapter(sameQRCodeAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    /**
     * gets a list of all players who have scanned code usernames
     * @return ArrayList<String> of all the players
     * who have scanned the current codes's usernames
     */
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

    /**
     * populated the items in the XML of the fragment
     * @param view current view
     */
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
                new ViewPhotoFragment(code.getImageString()).show(getFragmentManager(), "ViewPhoto");
                break;

            case R.id.coordView:
                //goto map layout
                Bundle bundle = new Bundle();
                double latitude = code.getLocation().getLatitude();
                double longitude = code.getLocation().getLongitude();
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                MapFragment mapFragment = new MapFragment();
                mapFragment.setArguments(bundle);
                Log.i("Bundle being sent:", String.valueOf(bundle));
                ((MainActivity) getActivity()).changeFragment(mapFragment);
                break;

            case R.id.deleteButton:
                userWallet.removeCode(code);
                userWallet.setCurrentCode(null);

                // update user in Firestore
                fireStore.updateUser(user);

                //update QRList
                qrList = QRList.getInstance();
                qrList.removeCode(code);

                //go back to playerWaller
                ((MainActivity) getActivity()).changeFragment(new ProfileFragment());
                break;

            case R.id.backButton:
                //go back to playerWallet or Map
                if (userWallet.getBackToMap()) {
                    userWallet.setBackToMap(false);
                    bundle = new Bundle();
                    latitude = code.getLocation().getLatitude();
                    longitude = code.getLocation().getLongitude();
                    bundle.putDouble("latitude", latitude);
                    bundle.putDouble("longitude", longitude);
                    mapFragment = new MapFragment();
                    mapFragment.setArguments(bundle);
                    Log.i("Bundle being sent:", String.valueOf(bundle));
                    ((MainActivity) getActivity()).changeFragment(mapFragment);
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