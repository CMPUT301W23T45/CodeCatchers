package com.example.lab_4_codecatchers;

public class Code {
    private int score;
    private String hash;
    private String humanName;
    private int humanImage;

    public Code() {
        this.hash = null;
        this.score = 0;
        this.humanName = null;
        this.humanImage = R.drawable.baseline_qr_code_2_24;
    }

    public Code(int score, String hash) {
        this.score = score;
        this.hash = hash;
        this.humanName = "Jim"; // TODO: change to nameFunction once implemented
        this.humanImage = R.drawable.baseline_qr_code_2_24;
    }

    //just used for testing
    public Code(int score, String hash, String humanName, int humanImage) {
        this.score = score;
        this.hash = hash;
        this.humanName = humanName;
        this.humanImage = humanImage;
    }

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
}