package com.example.lab_4_codecatchers;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Map;

/**
 * Representation of a QR Code or Barcode
 */
public class Code {
    private int score;
    private String hash;
    private String humanName;
    private int humanImage;
    private String image;
    private String comment;
    private GeoPoint location;
    private String coordinates;


    //CONSTRUCTORS

    /**
     * Empty constructor for Code
     * Sets all data to null or 0
     * Sets humanImage to generic QR image
     */
    public Code() {
        this.hash = null;
        this.score = 0;
        this.humanName = null;
        this.humanImage = R.drawable.baseline_qr_code_2_24;
        this.image = null;
        this.location = new GeoPoint(0, 0);
        this.comment = null;
        this.coordinates = null;
    }

    /**
     * Constructor for Code with known score and hash
     * Will calc. humanName and humanImage from hash
     * @param score score of QR code
     * @param hash hashCode of QR code
     */
    public Code(int score, String hash, String image, String comment, String coordinates) {
        this.score = score;
        this.hash = hash;
        this.humanName = generateHumanName(hash);
        this.image = image;
        this.comment = comment;
        this.coordinates = coordinates;
    }

    /**
     * Constructor used for testing
     * @param score score of QR code
     * @param hash hashCode of QR code
     * @param image string rep. of QR code
     * @param humanName human readable name for QR code
     */
    public Code(int score, String hash, String humanName, String image,String comment, String coordinates) {
        this.score = score;
        this.hash = hash;
        this.humanName = humanName;
        this.image = image;
        this.comment = comment;
        this.coordinates = coordinates;
    }

    //SETTERS AND GETTERS

    public String getImageString() {return image;}

    public void setImageString(String image) {this.image = image;}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public String getQRImage() {
        String ref = "https://api.dicebear.com/5.x/bottts-neutral/png?seed=" + this.hash;
        return ref;
    }

    public void setComment(String comment){ this.comment = comment;}

    public  String getComment() {return comment;}

    public void setLocation(GeoPoint l) {
        this.location = l;
    }

    public GeoPoint getLocation() {
        return location;
    }

    //FUNCTIONS
    /**
     * Creates a human readable name from hash code
     * @param hash hashCode of QR code
     */
    private String generateHumanName(String hash) {
        String[] mentalAdjectives = {"Clever ", "Intelligent ", "Inquisitive ", "Curious ", "Creative ", "Wise ", "Analytical ", "Perceptive ", "Insightful ", "Reflective ", "Contemplative ", "Philosophical ", "Eloquent ", "Expressive ", "Imaginative ", "Visionary "};
        String[] physicalAdjectives = {"Slender ", "Muscular ", "Chubby ", "Obese ", "Lean ", "Fit ", "Tall ", "Short ", "Curvy ", "Athletic ", "Stout ", "Brawny ", "Fragile ", "Graceful ", "Flabby ", "Lanky "};
        String[] colours = {"Red ", "Orange ", "Yellow ", "Green ", "Blue ", "Purple ", "Pink ", "Brown ", "Gray ", "Black ", "White ", "Turquoise ", "Magenta ", "Gold ", "Silver ", "Bronze "};
        String[] gemstones = {"Diamond ", "Ruby ", "Emerald ", "Sapphire ", "Topaz ", "Opal ", "Amethyst ", "Garnet ", "Peridot ", "Aquamarine ", "Pearl ", "Turquoise ", "Citrine ", "Agate ", "Quartz ", "Jade "};
        String[] animals = {"Lion", "Tiger", "Elephant", "Giraffe", "Gorilla", "Kangaroo", "Panda", "Squirrel", "Koala", "Lemur", "Platypus", "Hedgehog", "Sloth", "Meerkat", "Penguin", "Ostrich"};

        String output = "";
        String substr = hash.substring(0, 5);
        for (int i = 0; i < substr.length(); i++) {
            int index = Character.digit(substr.charAt(i), 16);
            String selection;
            if (i == 0) {
                selection = mentalAdjectives[index];
            } else if (i == 1) {
                selection = physicalAdjectives[index];
            } else if (i == 2) {
                selection = colours[index];
            } else if (i == 3) {
                selection = gemstones[index];
            } else {
                selection = animals[index];
            }
            output = output.concat(selection);
        }
        return output;
    }


}
