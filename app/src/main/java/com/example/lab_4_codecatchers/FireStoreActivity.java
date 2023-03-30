package com.example.lab_4_codecatchers;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Storing and fetching data from firestore database
 */
public class FireStoreActivity {
    private final FirebaseFirestore userDB = FirebaseFirestore.getInstance();
    private final CollectionReference userCollection = userDB.collection("Users");
    private final CollectionReference codeCollection = userDB.collection(" ");
    User currentUser = User.getInstance();
    QRList list = QRList.getInstance();
    private static FireStoreActivity instance = null;

    public static FireStoreActivity getInstance(){
        if(instance == null){
            instance = new FireStoreActivity();
        }
        return instance;
    }
    public FireStoreActivity() {

    }

    /**
     * Adds the user to the database
     * @param userToAdd User object to add to the db
     */
    public Task<Void> addUser(User userToAdd){
        return userCollection
                .document(userToAdd.getUsername())
                .set(userToAdd);

    }

    public Task<Void> updateUser(User user) {
        return userCollection
                .document(user.getUsername())
                .update("totalScore", user.getCollectedQRCodes().getTotal(),
                        "highestUniqueCode", user.getCollectedQRCodes().getHighestUniqueScore(),
                        "collectedQRCodes", user.getCollectedQRCodes());
    }
    public Task<Void> updateCodes(ArrayList<Code> codes) {
        return codeCollection
                .document("Codes")
                .update("allCodes", list.getCodes());
    }


    /**
     * Retrieves users based on Device id for login
     * @return an array of usernames that have used this device
     */
    public Task<QuerySnapshot> getUsersBasedOnDevice(){
        return userCollection.whereArrayContains("devices",currentUser.getId()).get();
    }

    public Task<QuerySnapshot> isUniqueUsername() {
        return userCollection.get();

    }

    public Task<QuerySnapshot> getCodes() {
        return codeCollection.get();
    }
}
