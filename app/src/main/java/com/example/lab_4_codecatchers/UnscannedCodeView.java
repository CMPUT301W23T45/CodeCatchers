package com.example.lab_4_codecatchers;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnscannedCodeView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnscannedCodeView extends Fragment implements View.OnClickListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QRHASH = "qrHash";

    private String qrHash = null;
    QRList  qrList = QRList.getInstance();
    MiniCode code;
    User user = User.getInstance();
    private RecyclerView recyclerView;

    public UnscannedCodeView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param qrHash Hash of QrCode to be displayed.
     * @return A new instance of fragment UnscannedCodeView.
     */
    public static UnscannedCodeView newInstance(String qrHash) {
        UnscannedCodeView fragment = new UnscannedCodeView();
        Bundle args = new Bundle();
        args.putString(ARG_QRHASH, qrHash);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qrHash = getArguments().getString(ARG_QRHASH);
            if(qrList.inList(qrHash) != -1){
                code = qrList.getCode(qrList.inList(qrHash));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unscanned_code_view, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //update QRList
        FireStoreActivity.getInstance().fillQRList();

        populateFields(view);

        ArrayList<String> players = getPlayers();

        ImageButton back = view.findViewById(R.id.backButton);
        Button location = view.findViewById(R.id.viewImageLocation);
        back.setOnClickListener(this);
        location.setOnClickListener(this);

        //fill recyclerView
        recyclerView = view.findViewById(R.id.SameUserList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        SameQRCodeAdapter sameQRCodeAdapter = new SameQRCodeAdapter(getContext(), players);
        recyclerView.setAdapter(sameQRCodeAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }


    /**
     * populated the items in the XML of the fragment
     * @param view current view
     */
    private void populateFields(View view) {
        TextView humanName = view.findViewById(R.id.codeHumanName);
        TextView coords = view.findViewById(R.id.coordView);
        double latitude = code.getLocation().getLatitude();
        double longitude = code.getLocation().getLongitude();
        String locationString = latitude + ", " + longitude;

        humanName.setText(code.getName());
        coords.setText(locationString);
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
            //remove current user from list so it doesn't get displayed
            if(list1.contains(user.getUsername())){
                list1.remove(user.getUsername());
            }
            return list1;
        }
    }

    /**
     * Defines behaviour when buttons are selected in UnscannedCodeView
     * @param v current view
     */
    @Override
    public void onClick(View v) {
        FireStoreActivity fireStore = FireStoreActivity.getInstance();
        switch (v.getId()) {
            case R.id.viewImageLocation:
                new ViewPhotoFragment(code.getLocPic()).show(getFragmentManager(), "ViewPhoto");
                break;

            case R.id.backButton:
                //go back to map
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

            default:
                break;
        }
    }
}