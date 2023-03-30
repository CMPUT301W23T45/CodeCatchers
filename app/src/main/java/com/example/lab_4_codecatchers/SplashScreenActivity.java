package  com.example.lab_4_codecatchers;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 *Screen Initializing and checking if user has logged in before
 */
@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    User currentUser = User.getInstance();
    QRList qrList = QRList.getInstance();
    FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
    }

    @Override
    protected void onStart(){
        super.onStart();
        deviceCheck();
    }

    /**
     * Checks to see if this device has already logged in
     * If user exists then sets the current user to data retrieved -> so it can be used all throughout the program
     */
    protected void deviceCheck(){
        currentUser.setId(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        fireStoreActivity.getUsersBasedOnDevice()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> usersUsingDevice = queryDocumentSnapshots.getDocuments();
                    if(usersUsingDevice.isEmpty()){
                        Log.d(TAG,"No User Exists!");
                        noUserExists();
                    }else{
                        //Since user exists get info of the player and set the currentUser playing to information retrieved
                        User current = usersUsingDevice.get(0).toObject(User.class);
                        assert current != null;
                        currentUser.setUsername(current.getUsername());
                        currentUser.setEmail(current.getEmail());
                        currentUser.setPhone(current.getPhone());
                        currentUser.setRank(current.getCollectedQRCodes().getSize());
                        currentUser.setCollectedQRCodes(current.getCollectedQRCodes());
                        Log.d(TAG,"User Exists!");
                        userExists();
                    }
                });
    }

    protected void fillQRList(){
        fireStoreActivity.getCodes()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> QRCodes = queryDocumentSnapshots.getDocuments();
                    if(QRCodes.isEmpty()){
                        qrList.setCodes(new ArrayList<MiniCode>());
                    }else{
                        ArrayList<MiniCode> codeList = new ArrayList<>();
                        for(int i = 0; i<QRCodes.size(); i++) {
                            MiniCode code = QRCodes.get(i).toObject(MiniCode.class);
                            assert code != null;
                            MiniCode toAdd = new MiniCode();
                            toAdd.setHash(code.getHash());
                            toAdd.setImage(code.getImage());
                            toAdd.setLocation(code.getLocation());
                            toAdd.setPlayersWhoScanned(code.getPlayersWhoScanned());
                            codeList.add(toAdd);
                        }
                        qrList.setCodes(codeList);
                    }
                });
    }


    /**
     * If user exists then direct to MainActivity
     */
    public void userExists(){
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * If user does not exist then direct to FirstSignInActivity
     */
    public void noUserExists(){
        Intent intent = new Intent(SplashScreenActivity.this, FirstSignInActivity.class);
        startActivity(intent);
        finish();
    }


}
