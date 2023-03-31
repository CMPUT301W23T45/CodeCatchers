package com.example.lab_4_codecatchers;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Storing and fetching data from firestore database
 */
public class FireStoreActivity {
    private final FirebaseFirestore userDB = FirebaseFirestore.getInstance();
    private final CollectionReference userCollection = userDB.collection("Users");
    private final CollectionReference codeCollection = userDB.collection("qrCollect");
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

    public Task<Void> addCode(MiniCode code){
        return codeCollection
                .document(code.getHash())
                .set(code);

    }
    public Task<Void> updateCodes(MiniCode code) {
        return codeCollection
                .document(code.getHash())
                .update("locPic", code.getLocPic(), "location", code.getLocation(), "playersWhoScanned", code.getPlayersWhoScanned());
    }

    public Task<Void> removeCode(MiniCode code) {
        Log.i("CodeCatchers", "In Remove");
        return codeCollection
                .document(code.getHash())
                .delete();
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

    protected void fillQRList(){
        getCodes()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> QRCodes = queryDocumentSnapshots.getDocuments();
                    if(QRCodes.isEmpty()){
                        list.setCodes(new ArrayList<MiniCode>());
                    }else{
                        ArrayList<MiniCode> codeList = new ArrayList<>();
                        for(int i = 0; i<QRCodes.size(); i++) {
                            MiniCode code = QRCodes.get(i).toObject(MiniCode.class);
                            assert code != null;
                            MiniCode toAdd = new MiniCode();
                            toAdd.setHash(code.getHash());
                            toAdd.setLocPic(code.getLocPic());
                            toAdd.setLocation(code.getLocation());
                            toAdd.setPlayersWhoScanned(code.getPlayersWhoScanned());
                            codeList.add(toAdd);
                        }
                        list.setCodes(codeList);
                    }
                });
    }
}
