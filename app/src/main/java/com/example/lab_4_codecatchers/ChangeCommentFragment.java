package com.example.lab_4_codecatchers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * ChangeCommentFragment creates a dialog fragment to allow user to change the comment on a QR code
 */
public class ChangeCommentFragment extends DialogFragment {
    Code code;
    FireStoreActivity fireStore = FireStoreActivity.getInstance();
    User user = User.getInstance();

    public ChangeCommentFragment(Code code) {
        this.code = code;
    }

    public ChangeCommentFragment (int contentLayoutId, Code code) {
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.change_comment, null);
        EditText comment = view.findViewById(R.id.comment);
        comment.setText(code.getComment());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Change Comment")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add Comment", (dialog, which) -> {
                    String comment1 = comment.getText().toString().trim();
                    code.setComment(comment1);
                    // update user in Firestore
                    fireStore.updateUser(user);
                    ((MainActivity) getActivity()).changeFragment(new CodeViewFragment());
                }).create();
    }

}
