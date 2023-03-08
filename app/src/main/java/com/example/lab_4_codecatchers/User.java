package com.example.lab_4_codecatchers;

import java.util.ArrayList;

/**
 * Creates a user object for the database
 */
public class User {
    private static User instance = null;
    private String id;
    private String username;
    private String email;
    private String phone;
    private Integer totalScore;
    private Integer rank;
    private ArrayList<String> collectedQRCodes = new ArrayList<>();
    private ArrayList<String> devices = new ArrayList<>();

    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the devices for an user
     * @return devices
     */
    public ArrayList<String> getDevices() {
        return devices;
    }

    /**
     * Adds devices to that user
     * @param devices
     */
    public void setDevices(ArrayList<String> devices) {
        this.devices = devices;
    }



    public User() {

    }

    /**
     * Gets the username for an user
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets a username for a user
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the email for that user
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets an email for an user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of that user
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets users phone number
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the total score of the user
     * @return the total score
     */
    public Integer getTotalScore() {
        return totalScore;
    }

    /**
     * Sets the total score to an interger
     * @param totalScore number we want to the total score to be
     */
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Gets the rank for this user
     * @return rank number
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Sets the rank to this number
     * @param rank number
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * Retrieves all the collected QR codes of the user
     * @return collected QR codes
     */
    public ArrayList<String> getCollectedQRCodes() {
        return collectedQRCodes;
    }

    /**
     * Sets the collected QR codes of user
     * @param collectedQRCodes
     */
    public void setCollectedQRCodes(ArrayList<String> collectedQRCodes) {
        this.collectedQRCodes = collectedQRCodes;
    }

}