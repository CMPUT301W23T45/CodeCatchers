package com.example.lab_4_codecatchers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder>{
    Context context;
    ArrayList<Code> codes; //List of codes to be put in recyclerView

    public ProfileAdapter(Context context, ArrayList<Code> codes) {
        this.context = context;
        this.codes = codes;
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
        //holder.qrImage.setImageResource(code.getHumanImage());
        // TODO: Figure out image files


    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return codes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //ShapeableImageView qrImage;
        TextView nameText;
        TextView scoreText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //qrImage = itemView.findViewById(R.id.qrImage);
            nameText = itemView.findViewById(R.id.humanName);
            scoreText = itemView.findViewById(R.id.qrScore);
        }
    }
}
