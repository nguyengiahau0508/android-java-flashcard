package com.example.flashcard.data.model;

import java.io.Serializable;

public class Card implements Serializable {
    private int id;
    private int deckId;
    private String frontText;
    private String backText;
    private String imagePath;

    public Card(int id, int deckId, String frontText, String backText, String imagePath) {
        this.id = id;
        this.deckId = deckId;
        this.frontText = frontText;
        this.backText = backText;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public int getDeckId() {
        return deckId;
    }

    public String getFrontText() {
        return frontText;
    }

    public String getBackText() {
        return backText;
    }

    public String getImagePath() {
        return imagePath;
    }
}

