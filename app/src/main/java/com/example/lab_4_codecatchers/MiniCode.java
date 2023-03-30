package com.example.lab_4_codecatchers;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Map;

public class MiniCode {
    private String hash;
    private String image;
    private GeoPoint location;
    private ArrayList<String> playersWhoScanned;

    //CONSTRUCTORS

    public MiniCode() {
        this.hash = null;
        this.image = null;
        this.location = null;
        this.playersWhoScanned = new ArrayList<String>();
    }

    public MiniCode(String hash, String image, GeoPoint location, ArrayList<String> playersWhoScanned) {
        this.hash = hash;
        this.image = image;
        this.location = location;
        this.playersWhoScanned = playersWhoScanned;
    }

    //SETTER AND GETTER

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public ArrayList<String> getPlayersWhoScanned() {
        return playersWhoScanned;
    }

    public void setPlayersWhoScanned(ArrayList<String> playersWhoScanned) {
        this.playersWhoScanned = playersWhoScanned;
    }

    public void addPlayer(String username) {
        playersWhoScanned.add(username);
    }

    //Returns -1 if error, 0 if no players left after removing, 1 if more players left
    public int removePlayer(String username){
        if(playersWhoScanned.contains(username)) {
            playersWhoScanned.remove(username);
            if(playersWhoScanned.isEmpty()){
                return 0;
            }
            return 1;
        }
        return -1;
    }
}
