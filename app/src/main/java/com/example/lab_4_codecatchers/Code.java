package com.example.lab_4_codecatchers;

public class Code {
    private int score;
    private int hash;
    private String humanName;
    private int humanImage;

    public Code() {
        this.hash = 0;
        this.score = 0;
        this.humanName = null;
        this.humanImage = R.drawable.baseline_qr_code_2_24;
    }

    public Code(int score, int hash, String humanName, int humanImage) {
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

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }
}