package com.example.lab_4_codecatchers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * MapFragment shows a map with scanned QR codes
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    private User user;
    private HashMap<String, GeoPoint> allCodes = new HashMap<String, GeoPoint>();
    // Default location at the UAlberta Computer Science building
    private LatLng defaultPosition = new LatLng(53.526790646055474, -113.52714133200335);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        setUserInfo();
        return rootView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, 16F));
        FireStoreActivity db = FireStoreActivity.getInstance();
        db.getCodes().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot code : queryDocumentSnapshots) {
                GeoPoint g = (GeoPoint) code.get("location");
                if (g.getLatitude() != 0.0 && g.getLongitude() != 0.0) {
                    allCodes.put(code.get("name").toString(), g);
                }
            }
        });
        for (String n: allCodes.keySet()) {
            LatLng position = new LatLng(allCodes.get(n).getLatitude(), allCodes.get(n).getLongitude());
            map.addMarker(new MarkerOptions().position(position).title(n).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            Log.i("CodeCatchers", n);
        }
    }

    private void setUserInfo() {
        user = User.getInstance();
    }
}