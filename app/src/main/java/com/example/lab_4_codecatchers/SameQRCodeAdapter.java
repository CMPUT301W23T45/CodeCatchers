package com.example.lab_4_codecatchers;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class SameQRCodeAdapter extends RecyclerView.Adapter<SameQRCodeAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> listOfScanned; //List of codes to be put in recyclerView
    private ProfileAdapter.ItemClickListener clickListener;

    public SameQRCodeAdapter(Context context, ArrayList<String> listOfScanned) {
        this.context = context;
        this.listOfScanned= listOfScanned;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.code_list_items,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String key = listOfScanned.get(position);
        holder.playerName.setText(key);


    }

    @Override
    public int getItemCount() {
        return listOfScanned.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView playerName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
        }
    }
}
