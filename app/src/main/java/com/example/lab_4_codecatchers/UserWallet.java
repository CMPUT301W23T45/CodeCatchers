package com.example.lab_4_codecatchers;

import java.util.ArrayList;

public class UserWallet {
    private String userID;
    private ArrayList<Code> userCodes;


    //CONSTRUCTORS
    public UserWallet(String userID) {
        this.userID = userID;
        this.userCodes = new ArrayList<Code>();
    }

    public UserWallet(String userID, ArrayList<Code> playerCodes) {
        this.userID = userID;
        this.userCodes = playerCodes;
    }


    //GETTERS AND SETTERS

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<Code> getUserCodes() {
        return userCodes;
    }

    public void setUserCodes(ArrayList<Code> userCodes) {
        this.userCodes = userCodes;
    }

    public int getSize() {
        return userCodes.size();
    }

    //FUNCTIONS

    /**
     * Returns the total score (int) of all QR codes in wallet
     * @return  total score
     */
    public int getTotal() {
        int size = getSize();
        int total = 0;
        for(int i =0; i<size; i++) {
            int score = userCodes.get(i).getScore();
            total += score;
        }
        return total;
    }

    /**
     * Returns the highest score (int) of all QR codes in wallet
     * @return highest score
     */
    public int getHighest() {
        int size = getSize();
        int highest = userCodes.get(0).getScore();
        for(int i =1; i<size; i++) {
            int score = userCodes.get(i).getScore();
            if(score > highest) {highest = score;}
        }
        return highest;
    }

    /**
     * Returns the lowest score (int) of all QR codes in wallet
     * @return lowest score
     */
    public int getLowest() {
        int size = getSize();
        int lowest = userCodes.get(0).getScore();
        for(int i =1; i<size; i++) {
            int score = userCodes.get(i).getScore();
            if(score < lowest) {lowest = score;}
        }
        return lowest;
    }

    /**
     * Checks if code is in a wallet then, adds a Code to the wallet
     * @param code QR code to be added
     *
     */
    public void addCode(Code code) {
        if(userCodes.contains(code)) {
            //check if code has already been added
            return;
        }
        userCodes.add(code);
    }

    /**
     * Checks if code is in a wallet then, removes a Code to the wallet
     * @param code QR code to be removed
     * @return true if successful, false if failed
     */
    public boolean removeCode(Code code) {
        if(userCodes.contains(code)){
            userCodes.remove(code);
            return true;
        }else{
            return false;
        }
    }

}
