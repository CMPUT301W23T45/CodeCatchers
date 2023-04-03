package com.example.lab_4_codecatchers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
/**
 * This class acts as an adapter for OtherPlayerList list.
 */
public class OtherPlayerListAdapter extends ArrayAdapter<User> {
    private ArrayList<User> players;
    private Context context;
    private int myResource;
    /**
     * Constructor for OtherPlayerListAdapter.
     * @param context
     * Expects object from Context class.
     * @param resource
     * Expects object from Integer class.
     * @param players
     * Expects ArrayList of type Player.
     */
    public OtherPlayerListAdapter(Context context, int resource, ArrayList<User> players){
        super(context,resource,players);
        this.players = players;
        this.myResource = resource;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(myResource, parent, false);
        }

        User player = players.get(position);

        TextView username = view.findViewById(R.id.username);
        TextView totalPoints = view.findViewById(R.id.totalPoints);
        TextView currentRank = view.findViewById(R.id.currentRank);

        username.setText(player.getUsername());
        totalPoints.setText("Total Points: " + String.valueOf(player.getTotalScore()));
        currentRank.setText("Rank: " + Integer.toString(player.getRank()));

        return view;
    }

}
