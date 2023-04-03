package com.example.lab_4_codecatchers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter for the recycleView in LeaderBoardFragment
 *
 * @see LeaderBoardFragment
 */
public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {

    Context context;
    private ArrayList<User> leaderBoardInfo = new ArrayList<>();
    private ItemClickListener clickListener;

    public LeaderBoardAdapter(Context context,ArrayList<User> leaderBoardInfo, ItemClickListener clickListener) {
        this.leaderBoardInfo = leaderBoardInfo;
        this.context = context;
        int size = leaderBoardInfo.size();
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
        View view = LayoutInflater.from(context).inflate(R.layout.leader_board_recycle_view, parent, false);
        return new LeaderBoardAdapter.MyViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull LeaderBoardAdapter.MyViewHolder holder, int position) {
        User user = leaderBoardInfo.get(position);
        holder.userNameLeaderBoard.setText(leaderBoardInfo.get(position).getUsername());
        holder.rankLeaderBoard.setText(String.valueOf(leaderBoardInfo.get(position).getRank()));
        holder.scoreLeaderBoard.setText(String.valueOf(leaderBoardInfo.get(position).getTotalScore()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(user);
            }
        });
    }

    /**
     * gets number of items in codes ArrayList
     * @return int size of array list
     */
    @Override
    public int getItemCount() {
        return leaderBoardInfo.size();
    }


    /**
     * Changes the array according to users search input
     * @param usersList
     */
    public void searchList(ArrayList<User> usersList){
        leaderBoardInfo = usersList;
        notifyDataSetChanged();
    }

    /**
     * View Holder for list in recycle view
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userNameLeaderBoard;
        TextView rankLeaderBoard;
        TextView scoreLeaderBoard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameLeaderBoard = itemView.findViewById(R.id.usernameLeaderBoard);
            rankLeaderBoard = itemView.findViewById(R.id.LeaderBoardRank);
            scoreLeaderBoard = itemView.findViewById(R.id.leaderBoardEditScore);
        }

    }

    /**
     * Interface for ClickListener for items in LeaderBoard
     */
    public interface ItemClickListener {
        public void onItemClick(User user);
    }
}
