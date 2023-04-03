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
    private UserWallet collectedQRCodes;
    private ArrayList<String> devices = new ArrayList<>();
    private Integer highestUniqueCode;

    //FUNCTIONS

    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

    //CONSTRUCTORS

    /**
     * Empty constructor for User
     * Sets all data to empty data or 0
     */
    public User() {
        this.id = "0";
        this.username = " ";
        this.email = " ";
        this.phone = " ";
        this.totalScore = 0;
        this.highestUniqueCode = 0;
        this.rank = 0;
        this.collectedQRCodes = new UserWallet(this.id);
        this.devices = new ArrayList<String>();
    }

    /**
     * User constructor with all attributes known but devices
     * Devices is set to empty ArrayList
     * @param id user's ID
     * @param username user's username
     * @param email user's email
     * @param phone user's phone number
     * @param totalScore user's total points from scanned QR codes
     * @param rank user's rank on global leaderboard
     * @param collectedQRCodes list of all user's scanned QR codes
     */
    public User(String id, String username, String email, String phone, Integer totalScore, Integer rank, ArrayList<Code> collectedQRCodes, Integer highestUniqueCode) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.totalScore = totalScore;
        this.rank = rank;
        this.collectedQRCodes = new UserWallet(id, collectedQRCodes);
        this.devices = new ArrayList<String>();
        this.highestUniqueCode = highestUniqueCode;
    }

    //SETTERS AND GETTERS
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
    public UserWallet getCollectedQRCodes() {
        return collectedQRCodes;
    }

    /**
     * Sets the collected QR codes of user
     * @param collectedQRCodes
     */
    public void setCollectedQRCodes(UserWallet collectedQRCodes) {
        this.collectedQRCodes = collectedQRCodes;
    }

    public Integer getHighestUniqueCode() {
        return highestUniqueCode;
    }

    public void setHighestUniqueCode(Integer highestUniqueCode) {
        this.highestUniqueCode = highestUniqueCode;
    }

}
