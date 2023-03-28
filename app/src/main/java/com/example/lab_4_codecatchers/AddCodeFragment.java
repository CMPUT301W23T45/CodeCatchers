package com.example.lab_4_codecatchers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.GeoPoint;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.Result;

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
    ImageView ivProfile;
    ImageView addQRImage;
    Bitmap finalPhoto;
    Boolean photoAdded = false;
    private FusedLocationProviderClient fusedLocationClient;

    public AddCodeFragment() {
        // Required empty public constructor
    }

    private GeoPoint getLocation() {
        // Default value = 0, 0 (Null location)
        final double[] lat = {0};
        final double[] lon = {0};

        // Check permissions
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        lat[0] = location.getLatitude();
                        lon[0] = location.getLongitude();

                    }
                }
            });
        }

        Log.i("CodeCatchers_LAT", Double.toString(lat[0]));
        Log.i("CodeCatchers_LON", Double.toString(lon[0]));
        return new GeoPoint(lat[0], lon[0]);
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
        Button add_loc_photo = view.findViewById(R.id.add_loc_photoButton);
        ivProfile = view.findViewById(R.id.ivProfile);

        add_loc_photo.setOnClickListener(this);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data !=null){
            Bundle bundle = data.getExtras();
            finalPhoto = (Bitmap) bundle.get("data");
            ivProfile.setImageBitmap(finalPhoto);
        }
    }

    /**
     * Fill the information boxes for new QR code found in the AddCodeFragment xml
     * @param view current view
     */
    private void populateFields(View view) {
        TextView humanName = view.findViewById(R.id.addHumanName);
        TextView score = view.findViewById(R.id.addScore);
        addQRImage = view.findViewById(R.id.addQRImage);

        user = User.getInstance();
        userWallet = user.getCollectedQRCodes();
        code = userWallet.getCode((userWallet.getSize()) - 1);

        humanName.setText(code.getHumanName());
        score.setText(String.valueOf(code.getScore()));

        String loco = code.getQRImage();

        Picasso.get()
                .load(loco)
                .resize(130, 130)
                .centerCrop()
                .into(addQRImage);


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


//            case R.id.add_loc_photo:
//                // TODO: jump to photo taking camera fragment
//                break;
            case R.id.addButton:
                //The switches are returning a NullPointerException when using isChecked()
                //Commented out for now
                if(geoSave.isChecked()) {
                    GeoPoint l = getLocation();
                    code.setLocation(l);
                }

                if(photoAdded) {
                    // Convert the finalPhoto Bitmap to a Base64 encoded String
                    String encodedImage = null;
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    finalPhoto.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    byte[] imageBytes = outputStream.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    //TODO: upload encodedImage to firebase
                    code.setImageString(encodedImage);
                } else {
                    code.setImageString(" ");
                }

                // here is the code to convert from string back to bitmap
//                String bitmapString = "get from firebase" // Replace with bitmap string
//                byte[] decodedBytes = Base64.decode(bitmapString, Base64.DEFAULT);
//                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                // Get user comment from EditText
                EditText commentField = getView().findViewById(R.id.editTextComment);
                String comment = commentField.getText().toString().trim();
                if (!TextUtils.isEmpty(comment)) {
                    // store the comment
                    // Set user comment to Code
                    code.setComment(comment);
                }


                // update user in Firestore
                FireStoreActivity fireStore = FireStoreActivity.getInstance();
                fireStore.updateUser(user);
                ((MainActivity) getActivity()).changeFragment(new CameraFragment());

                break;

            case R.id.cancelButton:
                //remove Code from wallet
                userWallet.removeCode(code);
                ((MainActivity) getActivity()).changeFragment(new CameraFragment());
                break;

            case R.id.add_loc_photoButton:   //https://www.youtube.com/watch?v=YLUmfyGFjnU&ab_channel=CodingDemos
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(intent,1);
                    photoAdded = true;
                } else {
                    Toast.makeText(getActivity(), "There is no app that supports this action",Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                ((MainActivity) getActivity()).changeFragment(new CameraFragment());
                break;
        }
        //jump back to camera fragment
    }
}
