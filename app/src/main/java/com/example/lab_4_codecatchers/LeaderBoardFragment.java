package com.example.lab_4_codecatchers;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 * This fragment WILL show leaderboard
 * Not implemented for half-way
 *Implemented with the assistance of :
 * https://stackoverflow.com/questions/68423448/how-to-sort-an-array-of-objects-by-total-score
 * https://stackoverflow.com/questions/53991868/search-in-recyclerview-with-edittext
 */
public class LeaderBoardFragment extends Fragment {

    RecyclerView recyclerView;
    TextView globalrank;
    EditText searchPlayers;
    private final User currentUser = User.getInstance();
    private final FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();
    private ArrayList<User> allUsers = new ArrayList<>();
    LeaderBoardAdapter leaderBoardAdapter = new LeaderBoardAdapter(getContext(),allUsers);

    public LeaderBoardFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader_board, container, false);
    }



    /**
     * Fills the array allUsers with users from firebase
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allUsers.clear();
        fireStoreActivity.isUniqueUsername()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    queryDocumentSnapshots.getDocuments().forEach(documentSnapshot -> allUsers.add(documentSnapshot.toObject(User.class)));
                    int rank = rankByScore(allUsers);
                    globalrank.setText("" + rank);

                    Log.d(TAG,"users are "+ allUsers);
                    recyclerView = view.findViewById(R.id.users_recycle_view);
                    leaderBoardAdapter = new LeaderBoardAdapter(getContext(),allUsers);
                    recyclerView.setAdapter(leaderBoardAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                });

        //setting recycler view to show list of players
        globalrank = view.findViewById(R.id.editglobalrank);
        searchPlayers = view.findViewById(R.id.editTextSearchForPlayers);
        searchPlayers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search(editable.toString());
            }
        });
    }

    /**
     * Array filters according to the users input
     * @param edit
     */
    private void search(String edit){
        ArrayList<User> users = new ArrayList<>();
        for(User user : allUsers){
            if(user.getUsername().toLowerCase(Locale.ROOT).contains(edit.toLowerCase(Locale.ROOT))){
                users.add(user);
            }
        }
        leaderBoardAdapter.searchList(users);
    }


    /**
     * Used to rank each user in order
     * @param users
     * @return users rank number
     */
    private int rankByScore(ArrayList<User> users){
        users.sort((user,i)-> i.getTotalScore() - user.getTotalScore());
        for(int i =0;i < users.size();i++){
            users.get(i).setRank(i+1);
        }
        for(int i =0;i < users.size();i++){
            if(users.get(i).getUsername().equals(currentUser.getUsername())){
                return i+1;
            }
        }
        return 0;
    }



}