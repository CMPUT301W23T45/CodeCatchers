package com.example.lab_4_codecatchers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Adapter for the recycleView in ProfileFragment and OtherPlayer
 * Implemented with assistance from: Foxandroid on YouTube
 *      URL: https://www.youtube.com/watch?v=UBgXVGgTaHk
 *      Author: Foxandroid
 *
 * @see ProfileFragment
 * @see OtherPlayer
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder>{
    Context context;
    ArrayList<Code> codes; //List of codes to be put in recyclerView
    private ItemClickListener clickListener;

    public ProfileAdapter(Context context, ArrayList<Code> codes, ItemClickListener clickListener) {
        this.context = context;
        this.codes = codes;
        this.clickListener = clickListener;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return MyViewHolder(view)
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_list_items, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Code code = codes.get(position);
        holder.scoreText.setText(String.valueOf(code.getScore()));
        holder.nameText.setText(code.getHumanName());
        String loco = code.getQRImage();
        Picasso.get()
                .load(loco)
                .resize(130, 130)
                .centerCrop()
                .into(holder.qrImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(code);
            }
        });
    }

    /**
     * gets number of items in codes ArrayList
     * @return int size of array list
     */
    @Override
    public int getItemCount() {
        return codes.size();
    }

    /**
     * View Holder for list in recycle view
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //ShapeableImageView qrImage;
        TextView nameText;
        TextView scoreText;
        ImageView qrImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            qrImage = itemView.findViewById(R.id.qrImage);
            nameText = itemView.findViewById(R.id.humanName);
            scoreText = itemView.findViewById(R.id.qrScore);
        }
    }

    public interface ItemClickListener {
        public void onItemClick(Code code);
    }
}
