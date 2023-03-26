package com.example.lab_4_codecatchers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPhotoFragment extends DialogFragment {
    Code code;

    public ViewPhotoFragment(Code code) {
        this.code = code;
    }

    public ViewPhotoFragment(int contentLayoutId, Code code) {
        super(contentLayoutId);
        this.code = code;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_view_photo, null);
        ImageView locationPhoto = view.findViewById(R.id.loc_photo);
        byte [] encodeByte = Base64.decode(code.getImageString(),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        locationPhoto.setImageBitmap(bitmap);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Location Photo")
                .setNegativeButton("Done", null)
                .setPositiveButton("Take New Photo", (dialog, which) -> {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(intent, 1);
                    } else {
                        Toast.makeText(getActivity(), "There is no app that supports this action", Toast.LENGTH_SHORT).show();
                    }

                }).create();
    }
}