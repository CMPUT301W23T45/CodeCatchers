package com.example.lab_4_codecatchers;

import java.util.ArrayList;
import java.util.Objects;

/**
 * UserWallet stores a Users scanned QR codes
 * It can also preform tasks, like getting highest QR codes
 */
public class UserWallet {
    private String userID;
    private Code currentCode;
    private ArrayList<Code> userCodes;


    //CONSTRUCTORS

    /**
     * Empty constructor for wallet
     * Sets userID to null and userCodes to empty ArrayList
     */
    public UserWallet() {
        this.userID = null;
        this.userCodes = new ArrayList<Code>();
        this.currentCode = null;
    }

    /**
     * Constructor for wallet with existing useID
     * Sets userCodes to empty ArrayList
     * @param userID id for user
     */
    public UserWallet(String userID) {
        this.userID = userID;
        this.userCodes = new ArrayList<Code>();
        this.currentCode = null;
    }

    /**
     * Constructor for wallet with existing useID and list of codes
     * @param userID id for user
     * @param playerCodes list of players scanned codes
     */
    public UserWallet(String userID, ArrayList<Code> playerCodes) {
        this.userID = userID;
        this.userCodes = playerCodes;
        this.currentCode = null;
    }


    //GETTERS AND SETTERS

    public Code getCurrentCode() {
        return currentCode;
    }

    public void setCurrentCode(Code currentCode) {
        this.currentCode = currentCode;
    }

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
     * Gets code at index if code is in a wallet then
     * @param index index of code to get
     * @return Code at index or null if index is out of range
     */
    public Code getCode(int index) {
        if(userCodes.size() <= index){
            return null;
        }
        return userCodes.get(index);
    }

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
    /**
     * Returns the highest score (int) of all QR codes in wallet
     * @return highest scoring Code
     */
    public Code getHighest() {
        int size = getSize();
        if(size <= 0) {
            return null;
        }
        int highest = userCodes.get(0).getScore();
        int highestIndex = 0;
        for(int i =1; i<size; i++) {
            int score = userCodes.get(i).getScore();
            if(score > highest) {
                highest = score;
                highestIndex = i;
            }
        }
        return userCodes.get(highestIndex);
    }

    public Integer getHighestUniqueScore() {
        int size = getSize();
        if(size <= 0) {
            return null;
        }
        int highest = userCodes.get(0).getScore();
        for(int i =1; i<size; i++) {
            int score = userCodes.get(i).getScore();
            if(score > highest) {
                highest = score;
            }
        }
        return highest;
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
        int lowest = userCodes.get(0).getScore();
        int lowestIndex = 0;
        for(int i =1; i<size; i++) {
            int score = userCodes.get(i).getScore();
            if(score < lowest) {
                lowest = score;
                lowestIndex = i;
            }
        }
        return userCodes.get(lowestIndex);
    }

    /**
     * Checks if code is in a wallet then, adds a Code to the wallet
     * @param code QR code to be added
     *
     */
    public void addCode(Code code) {
        //check if code has already been added
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

    public Boolean inWallet(String hash){
        if (userCodes.isEmpty()) {
            return false;
        }
        int size = userCodes.size();
        for (int i = 0; i < size; i++) {
            if (Objects.equals(userCodes.get(i).getHash(), hash)) {
                return true;
            }
        }
        return false;
    }
    public Code getUniqueCode() {
        //TODO: implement
        return null;
    }

}

