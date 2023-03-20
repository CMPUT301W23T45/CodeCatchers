package com.example.lab_4_codecatchers;

import java.util.ArrayList;

/**
 * UserWallet stores a Users scanned QR codes
 * It can also preform tasks, like getting highest QR codes
 */
public class UserWallet {
    private String userID;
    private ArrayList<Code> userCodes2;


    //CONSTRUCTORS

    /**
     * Empty constructor for wallet
     * Sets userID to null and userCodes to empty ArrayList
     */
    public UserWallet() {
        this.userID = null;
        this.userCodes2 = new ArrayList<Code>();
    }

    /**
     * Constructor for wallet with existing useID
     * Sets userCodes to empty ArrayList
     * @param userID id for user
     */
    public UserWallet(String userID) {
        this.userID = userID;
        this.userCodes2 = new ArrayList<Code>();
    }

    /**
     * Constructor for wallet with existing useID and list of codes
     * @param userID id for user
     * @param playerCodes list of players scanned codes
     */
    public UserWallet(String userID, ArrayList<Code> playerCodes) {
        this.userID = userID;
        this.userCodes2 = playerCodes;
    }


    //GETTERS AND SETTERS

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<Code> getUserCodes() {
        return userCodes2;
    }

    public void setUserCodes(ArrayList<Code> userCodes) {
        this.userCodes2 = userCodes;
    }

    public int getSize() {
        return userCodes2.size();
    }

    //FUNCTIONS

    /**
     * Gets code at index if code is in a wallet then
     * @param index index of code to get
     * @return Code at index or null if index is out of range
     */
    public Code getCode(int index) {
        if(userCodes2.size() <= index){
            return null;
        }
        return userCodes2.get(index);
    }

    /**
     * Returns the total score (int) of all QR codes in wallet
     * @return  total score
     */
    public int getTotal() {
        int size = getSize();
        int total = 0;
        for(int i =0; i<size; i++) {
            int score = userCodes2.get(i).getScore();
            total += score;
        }
        return total;
    }

    /**
     * Returns the highest score (int) of all QR codes in wallet
     * @return highest score
     */
    /**
     * Returns the highest score (int) of all QR codes in wallet
     * @return highest scoring Code
     */
    public Code getHighest() {
        int size = getSize();
        if(size <= 0) {
            return null;
        }
        int highest = userCodes2.get(0).getScore();
        int highestIndex = 0;
        for(int i =1; i<size; i++) {
            int score = userCodes2.get(i).getScore();
            if(score > highest) {
                highest = score;
                highestIndex = i;
            }
        }
        return userCodes2.get(highestIndex);
    }

    /**
     * Returns the lowest score (int) of all QR codes in wallet
     * @return lowest scoring Code
     */
    public Code getLowest() {
        int size = getSize();
        if(size <= 0) {
            return null;
        }
        int lowest = userCodes2.get(0).getScore();
        int lowestIndex = 0;
        for(int i =1; i<size; i++) {
            int score = userCodes2.get(i).getScore();
            if(score < lowest) {
                lowest = score;
                lowestIndex = i;
            }
        }
        return userCodes2.get(lowestIndex);
    }

    /**
     * Checks if code is in a wallet then, adds a Code to the wallet
     * @param code QR code to be added
     *
     */
    public void addCode(Code code) {
        if(userCodes2.contains(code)) {
            //check if code has already been added
            return;
        }
        userCodes2.add(code);
    }

    /**
     * Checks if code is in a wallet then, removes a Code to the wallet
     * @param code QR code to be removed
     * @return true if successful, false if failed
     */
    public boolean removeCode(Code code) {
        if(userCodes2.contains(code)){
            userCodes2.remove(code);
            return true;
        }else{
            return false;
        }
    }

}

