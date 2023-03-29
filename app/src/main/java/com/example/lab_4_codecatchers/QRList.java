package com.example.lab_4_codecatchers;

import java.util.ArrayList;
import java.util.Objects;

public class QRList {
    ArrayList<Code> codes;
    private static QRList instance = null;

    //CONSTRUCTORS
    public static QRList getInstance(){
        if(instance == null){
            instance = new QRList(new ArrayList<Code>());
        }
        return instance;
    }

    public QRList(ArrayList<Code> codes) {
        this.codes = codes;
    }

    //SETTERS AND GETTERS

    public ArrayList<Code> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<Code> codes) {
        this.codes = codes;
    }

    //FUNCTIONS
    public int inList(Code code) {
        if (codes.isEmpty()) {
            return -1;
        }
        int size = codes.size();
        for (int i = 0; i < size; i++) {
            if (Objects.equals(codes.get(i).getHash(), code.getHash())) {
                return i;
            }
        }
        return -1;
    }

    public boolean isUnique(Code code) {
        int index = inList(code);
        if (index == -1) {
            return true;
        } //list is empty or item is not in list
        Code code1 = codes.get(index);
        ArrayList<String> players = code1.getPlayersWhoScanned();
        if (players.size() > 1) {
            return false;
        } else {
            return true;
        }
    }

    public void addCode(Code code){
        int index = inList(code);
        if (index == -1) {
            //adds whole code to list
            codes.add(code);
        } else {
            //if the code with the same hash is already in list
            //then only add current player to list
            Code code1 = codes.get(index);
            User user = User.getInstance();
            String ID = user.getId();
            code1.addPlayer(ID);
        }

        //update Firestore
        FireStoreActivity.getInstance().updateCodes(codes);

    }

    public void removeCode(Code code){
        int index = inList(code);
        if (index != -1) {
            Code code1 = codes.get(index);
            User user = User.getInstance();
            String ID = user.getId();
            int i = code1.removePlayer(ID);
            if(i == 0) {
                //no more players have in their wallet
                codes.remove(code1);
            }
        }

        //update Firestore
        FireStoreActivity.getInstance().updateCodes(codes);
    }
}