package com.example.lab_4_codecatchers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * QRList stores a global list of scanned codes (in MiniCode format)
 * It can also preform tasks, like adding, removing and checking if code is in List
 * @see MiniCode
 */
public class QRList {
    ArrayList<MiniCode> codes;
    private static QRList instance = null;

    //CONSTRUCTORS
    public static QRList getInstance(){
        if(instance == null){
            instance = new QRList(new ArrayList<MiniCode>());
        }
        return instance;
    }

    public QRList(ArrayList<MiniCode> codes) {
        this.codes = codes;
    }

    //SETTERS AND GETTERS

    public ArrayList<MiniCode> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<MiniCode> codes) {
        this.codes = codes;
    }

    //FUNCTIONS
    public int getSize(){
        return codes.size();
    }

    /**
     * Checks if code is in a wallet
     * @param hash Hash code of MiniCode
     * @return -1 if not in List, otherwise the index of MiniCode in List
     */
    public int inList(String hash) {
        if (codes.isEmpty()) {
            return -1;
        }
        int size = codes.size();
        for (int i = 0; i < size; i++) {
            if (Objects.equals(codes.get(i).getHash(),hash)) {
                return i;
            }
        }
        return -1;
    }

    public MiniCode getCode(int index){
        return codes.get(index);
    }


    /**
     * Checks if MiniCode has been scanned by any other player
     * @param code Mini QR code to be removed
     */
    public boolean isUnique(MiniCode code) {
        int index = inList(code.getHash());
        if (index == -1) {
            return true;
        } //list is empty or item is not in list
        MiniCode code1 = codes.get(index);
        ArrayList<String> players= code1.getPlayersWhoScanned();
        if (players.size() > 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if MiniCode with same hash as code is in a List
     * then, adds MiniCode to list if not
     * If code is already in List then just the players userName will
     * be added to MiniCode's playersWhoScanned list
     * @param code QR code to be removed
     */
    public void addCode(Code code){
        User user = User.getInstance();
        String userName = user.getUsername();
        FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();
        int index = inList(code.getHash());
        if (index == -1) {
            //adds whole code to list
            MiniCode mini = new MiniCode(code.getHumanName(), code.getHash(), code.getImageString(), code.getLocation(), new ArrayList<String>());
            mini.addPlayer(userName);
            codes.add(mini);

            //update Firestore
            fireStoreActivity.addCode(mini);

        } else {
            //if the code with the same hash is already in list
            //then only add current player to list
            MiniCode code1 = codes.get(index);
            int score = user.getTotalScore();
            code1.addPlayer(userName);

            //update Firestore
            fireStoreActivity.updateCodes(code1);
        }

    }

    /**
     * Checks if MiniCode with same hash as code is in a List
     * then, removes MiniCode from list
     * @param code QR code to be removed
     */
    public void removeCode(Code code){
        FireStoreActivity fireStoreActivity = FireStoreActivity.getInstance();
        int index = inList(code.getHash());
        if (index != -1) {
            MiniCode code1 = codes.get(index);
            User user = User.getInstance();
            String userName = user.getUsername();
            int i = code1.removePlayer(userName);
            if(i == 0) {
                //no more players have in their wallet
                codes.remove(code1);
                //upadte FireStore by removing document
                fireStoreActivity.removeCode(code1);
            }else{
                //update Firestore
                fireStoreActivity.updateCodes(code1);
            }
        }

    }
}