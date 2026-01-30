package com.pnuDev.LostAndFound.model;

public enum Category {
    ELECTRONICS("Електроніка"),
    DOCUMENTS("Документи"),
    CLOTHING("Одяг"),
    ACCESSORIES("Аксесуари"),
    OTHER("Інше");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}