package com.example.flashcard.data.model;

import java.io.Serializable;

public class Deck implements Serializable {
    private int id;
    private String name;
    private String description;
    private int cardCount;

    public Deck(int id, String name, String description, int cardCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cardCount = cardCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCardCount() {
        return cardCount;
    }
}
