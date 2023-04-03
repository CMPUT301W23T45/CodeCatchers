package com.example.lab_4_codecatchers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;



import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * MapFragment shows a map with scanned QR codes
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    private User user;
    private HashMap<String, GeoPoint> allCodes = new HashMap<String, GeoPoint>();
    // Default location at the UAlberta Computer Science building
    private LatLng defaultPosition = new LatLng(53.526790646055474, -113.52714133200335);
    private FusedLocationProviderClient fusedLocationClient;

    private void getLocation() {
        // Check bundle first
        Bundle bundle = getArguments();
        if (bundle != null) {
            LatLng bundleTarget = new LatLng(bundle.getDouble("latitude"), bundle.getDouble("longitude"));
            Log.i("CodeCatchers-LOC", String.valueOf(bundleTarget));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(bundleTarget, 16F));
            return;
        }

        // Check permissions
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i("CodeCatchers", "PERMISSIONS PASSED");
            fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Log.i("CodeCatchers", Double.toString(location.getLatitude()));
                        Log.i("CodeCatchers", Double.toString(location.getLongitude()));
                        LatLng userTarget = new LatLng(location.getLatitude(), location.getLongitude());
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userTarget, 16F));
                    } else {
                        Log.i("CodeCatchers", "LOCATION NOT FOUND");
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, 16F));
                    }
                }
            });
        }
    }

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Resources for the Map search: https://www.geeksforgeeks.org/how-to-add-searchview-in-google-maps-in-android/
        //Used for Reference: https://www.youtube.com/watch?v=68HWFGCSAj8
        //https://developers.google.com/maps/documentation/android-sdk/views
        // For fixing Import error: https://stackoverflow.com/questions/57484148/androidx-appcompat-widget-searchview-cannot-be-cast-to-android-widget-searchview
        super.onViewCreated(view, savedInstanceState);
        //update QRList
        FireStoreActivity.getInstance().fillQRList();


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        SearchView searchView = view.findViewById(R.id.searchView); //SearchView by ID in the fragment_map.xml file
        searchView.setQueryHint("Latitude,Longitude");
        //searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { //Handles user entry into the search bar.
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                String[] coordinates = query.split(",");
                if (coordinates.length != 2) {
                    Toast.makeText(requireContext(), "Invalid coordinates", Toast.LENGTH_SHORT).show();
                    return false;
                }
                //Parse the coordinates into doubles
                try {
                    double latitude = Double.parseDouble(coordinates[0]);
                    double longitude = Double.parseDouble(coordinates[1]);
                    LatLng location = new LatLng(latitude, longitude); //Create new object.
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16F)); //use the move camera method
                    return true;
                } catch (NumberFormatException e) { //If the parse failed.
                    Toast.makeText(requireContext(), "Invalid coordinates", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        getLocation();

        FireStoreActivity db = FireStoreActivity.getInstance();
        db.getCodes().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot code : queryDocumentSnapshots) {
                GeoPoint g = (GeoPoint) code.get("location");
                if (g.getLatitude() != 0.0 && g.getLongitude() != 0.0) {
                    LatLng position = new LatLng(g.getLatitude(), g.getLongitude());
                    BitmapDescriptor markerIcon;
                    if (user.getCollectedQRCodes().inWallet(code.get("hash").toString()) >= 0) {
                        markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                    } else {
                        markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                    }
                    map.addMarker(new MarkerOptions().position(position).title(code.get("name").toString()).icon(markerIcon)).setTag(code);
                }
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                DocumentSnapshot c = (DocumentSnapshot) marker.getTag();
                if (c != null) {
                    Log.i("CodeCatchers", c.get("name").toString());
                    int ind = user.getCollectedQRCodes().inWallet(c.get("hash").toString());
                    if (ind >= 0) {
                        user.getCollectedQRCodes().setCurrentCode(user.getCollectedQRCodes().getCode(ind));
                        user.getCollectedQRCodes().setBackToMap(true);
                        ((MainActivity) getActivity()).changeFragment(new CodeViewFragment());
                    } else {
                        Log.i("CodeCatchers", "Code not scanned by current user, redirecting to UnscannedCodeView");
                        UnscannedCodeView unscanned = UnscannedCodeView.newInstance(c.get("hash").toString());
                        ((MainActivity) getActivity()).changeFragment(unscanned);
                    }
                }
                //((MainActivity) getActivity()).changeFragment(new CodeViewFragment());
                return false;
            }
        });
    }

    private void setUserInfo() {
        user = User.getInstance();
    }
}