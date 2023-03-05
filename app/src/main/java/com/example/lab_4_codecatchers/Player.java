package com.example.codecatchers;

import java.util.ArrayList;

/**
 * Handles the player information across the app
 */
public class Player {
    private static Player instance = null;
    private String username;
    private String email;
    private String phone;
    private String id;

    public static Player getInstance(){
        if(instance == null){
            instance = new Player();
        }
        return instance;
    }

    /**
     * Retrieves the username of the player playing the app
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username to an String
     * @param username the String we want the username to be
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the email of the player playing the app
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email to some String provided
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the phone number of the player
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number to given string
     * @param phone the phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retrieves the device id of this player
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the device id to given String
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
}
