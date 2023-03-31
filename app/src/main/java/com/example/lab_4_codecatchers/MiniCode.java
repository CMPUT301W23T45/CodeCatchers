package com.example.lab_4_codecatchers;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class MiniCode {
    private String hash;
    private String name;
    private String locPic;
    private GeoPoint location;
    private ArrayList<String> playersWhoScanned;

    //CONSTRUCTORS

    public MiniCode() {
        this.name = null;
        this.hash = null;
        this.locPic = null;
        this.location = null;
        this.playersWhoScanned = new ArrayList<String>();
    }

    public MiniCode(String name, String hash, String locPic, GeoPoint location, ArrayList<String> playersWhoScanned) {
        this.name = name;
        this.hash = hash;
        this.locPic = locPic;
        this.location = location;
        this.playersWhoScanned = playersWhoScanned;
    }

    //SETTER AND GETTER


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getLocPic() {
        return locPic;
    }

    public void setLocPic(String locPic) {
        this.locPic = locPic;
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
