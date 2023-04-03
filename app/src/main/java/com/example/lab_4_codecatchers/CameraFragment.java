package com.example.lab_4_codecatchers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.lab_4_codecatchers.R;
import com.google.zxing.Result;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Fragment shows Camera that auto-scans QR codes
 * Also turns codes to hashes and sums Code's score
 */
public class CameraFragment extends Fragment {
    // declaring all the items from xml
    TextView tv_textView;
    UserWallet userWallet;

    // for camera - neel
    private CodeScanner mCodeScanner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CameraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        tv_textView = view.findViewById(R.id.tv_textView);

        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    // this function is to scan the QR code
                    public void run() {
                        /**
                         TODO potentially create the other fragment here to that we can save the qr code
                         */
                        // gets the decoded QR content
                        String s = result.getText();

                        // collected message passed to user
                        tv_textView.setText("Code Collected!!");

                        // gets the binary content of the decoded message
                        byte[] bytes = s.getBytes();
                        int score = getScore(bytes);

                        // gets the first item in the array and stores it as a string
                        String x = String.valueOf(bytes[0]);

                        // if the array is greater than size 1 than it goes through it and added it to the string x
                        if (bytes.length > 1) {
                            for (int i = 1; i < bytes.length; i++) {
                                x = x + String.valueOf(bytes[i]);
                            }
                        }

                        // inputs x as the input for the hash function to get the hash output and sets it under hashOut_textView
                        String hash_output = hash(x);

                        userWallet = User.getInstance().getCollectedQRCodes();
                        int inWallet = userWallet.inWallet(hash_output);
                        if(inWallet >= 0) {
                            Toast.makeText(getActivity(), "You already have this code!",Toast.LENGTH_SHORT).show();
                            ((MainActivity) getActivity()).changeFragment(new CameraFragment());
                        } else {
                            //make new code
                            Code code = new Code(score, hash_output, "0", "", "");
                            userWallet.addCode(code);
                            ((MainActivity) getActivity()).changeFragment(new AddCodeFragment());
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return view;
    }

    // Returns the SHA-256 hash for any given string
    // Based on code from http://www.java2s.com/example/android/java.lang/sha256-hash-string.html
    public static String hash(String s) {
        byte[] rawHash = null;
        String output = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            rawHash = digest.digest(s.getBytes());
        } catch (NoSuchAlgorithmException e) {
            Log.e("CodeCatchers", "Cannot calculate SHA-256");
        }
        if (rawHash != null) {
            StringBuilder sb = new StringBuilder();
            for (byte b: rawHash) {
                sb.append(String.format("%02x", b));
            }
            output = sb.toString();
        }
        return output;
    }

    public static int getScore(byte[] bytes) {
        int x = 0;
        for (byte b : bytes) {
            x += b;
        }
        int m = bytes.length % 100;
        int[] down = {11, 75, 42, 66, 5, 94, 32, 68, 19, 83};
        int[] up = {72, 39, 88, 15, 56, 93, 2, 47, 81, 29};
        int score;
        if (contains(up, m)) {
            score = x * m;
        } else if (contains(down, m)) {
            score = Math.floorDiv(x, 2);
        } else {
            score = x;
        }
        return score;
    }

    private static boolean contains(int[] arr, int val) {
        for (int i : arr) {
            if (i == val) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
