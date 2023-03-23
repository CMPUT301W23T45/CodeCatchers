package com.example.lab_4_codecatchers;

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
        this.comment = comment;
    }

    /**
     * Constructor for Code with known score and hash
     * Will calc. humanName and humanImage from hash
     * @param score score of QR code
     * @param hash hashCode of QR code
     */
    public Code(int score, String hash, String image, String comment) {
        this.score = score;
        this.hash = hash;
        this.humanName = generateHumanName(hash); // TODO: change to nameFunction once implemented
//        this.humanImage = R.drawable.baseline_qr_code_2_24; // TODO: change to imageFunction once implemented
        this.image = image;
        this.comment = comment;
    }

    /**
     * Constructor used for testing
     * @param score score of QR code
     * @param hash hashCode of QR code
     * @param image string rep. of QR code
     * @param humanName human readable name for QR code
     */
    public Code(int score, String hash, String humanName, String image,String comment) {
        this.score = score;
        this.hash = hash;
        this.humanName = humanName;
        this.image = image;
        this.comment = comment;
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

    public void setComment(String comment){ this.comment = comment;}

    public  String getComment() {return comment;}

    //FUNCTIONS
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

    private String[] generateFace(String hash) {
        String[] face= new String[8];
        int eyes = 3;
        int eyebrows = 2;
        int shape1= 0;
        int shape2= 1;
        int shape3 = 7;
        int nose1 = 4;
        int nose2 = 5;
        int smile = 6;
        int beard = 7;

        face[shape1] = "    ____";
        face[shape2]= "  /      \\";
        face[eyebrows]="  | ^  ^ |";
        face[eyes]="|\\| -  - |/|";
        face[nose1]=" \\|  ||  |/";
        face[nose2]="  |  ``  |";
        face[smile]="  | `__` |";
        face[shape3]="   \\____/";

    /*         base face
    0            ____
    1          /      \
    2          | ^  ^ |
    3        |\| -  - |/|
    4         \|  ||  |/
    5          |  ``  |
    6          | `__` |
    7           \____/
    */


        String substr = hash.substring(0, 6);

        // Set the eyes based on the first bit
        int index = Character.digit(substr.charAt(0), 2);
        if (index == 1){
            face[eyes]="|\\| O  O |/|";
        }

        // Set the eyebrows based on the second bit
        index = Character.digit(substr.charAt(1), 2);
        if (index == 1){
            face[eyebrows]="  | ^  ^ |";
        }

        // Set the face shape based on the third bit
        index = Character.digit(substr.charAt(2), 2);
        if (index == 1){
            face[shape1]="   ______";
            face[shape2]="  |      |";
            face[shape3]="  |______|";
        }

        // Set the nose based on the fourth bit
        index = Character.digit(substr.charAt(3), 2);
        if (index == 1){
            face[nose1] = " \\| |__| |/";
            face[nose2] ="  |      |";
        }

        // Set the smile based on the fifth bit
        index = Character.digit(substr.charAt(4), 2);
        if(index==1){
            face[smile] = "  | ,--, |";
        }

        // Set the beard based on the sixth bit
        index = Character.digit(substr.charAt(5), 2);
        if (index==1){
            face[beard]="  \\::::::/";
        }
        return face;
    }
}
