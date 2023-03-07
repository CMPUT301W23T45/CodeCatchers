
package com.example.lab_4_codecatchers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Storing and fetching data from firestore database
 */
public class FireStoreActivity {
    private final FirebaseFirestore userDB = FirebaseFirestore.getInstance();
    private final CollectionReference userCollection = userDB.collection("Users");
    User currentUser = User.getInstance();
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

    /**
     * Retrieves users based on Device id for login
     * @return an array of usernames that have used this device
     */
    public Task<QuerySnapshot> getUsersBasedOnDevice(){
        return userCollection.whereArrayContains("devices",currentUser.getId()).get();
    }

}