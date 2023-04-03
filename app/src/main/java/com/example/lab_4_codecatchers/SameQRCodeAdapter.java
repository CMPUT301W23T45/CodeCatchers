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

/**
 * Adapter for the recycleView in CodeViewFragment and UnscannedCodeView
 *
 * @see UnscannedCodeView
 * @see CodeViewFragment
*/
public class SameQRCodeAdapter extends RecyclerView.Adapter<SameQRCodeAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> listOfScanned; //List of codes to be put in recyclerView
    private ProfileAdapter.ItemClickListener clickListener;

    public SameQRCodeAdapter(Context context, ArrayList<String> listOfScanned) {
        this.context = context;
        this.listOfScanned= listOfScanned;

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
        View view = LayoutInflater.from(context).inflate(R.layout.code_list_items,parent, false);
        return new MyViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String key = listOfScanned.get(position);
        holder.playerName.setText(key);


    }

    /**
     * gets number of items in codes ArrayList
     * @return int size of array list
     */
    @Override
    public int getItemCount() {
        return listOfScanned.size();
    }

    /**
     * View Holder for list in recycle view
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView playerName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
        }
    }
}
