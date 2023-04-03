package com.example.lab_4_codecatchers;

/**
 * This class represents a global instance of the current player.
 */
public class SingletonPlayer {
    public static User player = new User();
    public static double lat = -1;  // player current location
    public static double lon = -1;  // player current location
    public SingletonPlayer() {
    }
}
