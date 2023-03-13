package com.example.lab_4_codecatchers;

/**
 * Representation of a QR Code or Barcode
 */
public class Code {
    private int score;
    private String hash;
    private String humanName;
    private int humanImage;

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
    }

    /**
     * Constructor for Code with known score and hash
     * Will calc. humanName and humanImage from hash
     * @param score score of QR code
     * @param hash hashCode of QR code
     */
    public Code(int score, String hash) {
        this.score = score;
        this.hash = hash;
        this.humanName = generateHumanName(hash); // TODO: change to nameFunction once implemented
        this.humanImage = R.drawable.baseline_qr_code_2_24; // TODO: change to imageFunction once implemented
    }

    /**
     * Constructor used for testing
     * @param score score of QR code
     * @param hash hashCode of QR code
     * @param humanImage visual rep. of QR code
     * @param humanName human readable name for QR code
     */
    public Code(int score, String hash, String humanName, int humanImage) {
        this.score = score;
        this.hash = hash;
        this.humanName = humanName;
        this.humanImage = humanImage;
    }

    //SETTERS AND GETTERS
    public int getHumanImage() {
        return humanImage;
    }

    public void setHumanImage(int humanImage) {
        this.humanImage = humanImage;
    }

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

    //FUNCTIONS
    private String generateHumanName(String hash) {
        String[] mentalAdjectives = {"Clever", "Intelligent", "Inquisitive", "Curious", "Creative", "Wise", "Analytical", "Perceptive", "Insightful", "Reflective", "Contemplative", "Philosophical", "Eloquent", "Expressive", "Imaginative", "Visionary"};
        String[] physicalAdjectives = {"Slender", "Muscular", "Chubby", "Obese", "Lean", "Fit", "Tall", "Short", "Curvy", "Athletic", "Stout", "Brawny", "Fragile", "Graceful", "Flabby", "Lanky"};
        String[] colours = {"Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Pink", "Brown", "Gray", "Black", "White", "Turquoise", "Magenta", "Gold", "Silver", "Bronze"};
        String[] gemstones = {"Diamond", "Ruby", "Emerald", "Sapphire", "Topaz", "Opal", "Amethyst", "Garnet", "Peridot", "Aquamarine", "Pearl", "Turquoise", "Citrine", "Agate", "Quartz", "Jade"};
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