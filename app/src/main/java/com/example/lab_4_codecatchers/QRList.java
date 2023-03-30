package com.example.lab_4_codecatchers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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

    public void addCode(MiniCode code){
        int index = inList(code.getHash());
        if (index == -1) {
            //adds whole code to list
            codes.add(code);
        } else {
            //if the code with the same hash is already in list
            //then only add current player to list
            MiniCode code1 = codes.get(index);
            User user = User.getInstance();
            String userName = user.getUsername();
            int score = user.getTotalScore();
            code1.addPlayer(userName);
        }

        //update Firestore

    }

    public void removeCode(MiniCode code){
        int index = inList(code.getHash());
        if (index != -1) {
            MiniCode code1 = codes.get(index);
            User user = User.getInstance();
            String userName = user.getUsername();
            int i = code1.removePlayer(userName);
            if(i == 0) {
                //no more players have in their wallet
                codes.remove(code1);
            }
        }

        //update Firestore
    }
}