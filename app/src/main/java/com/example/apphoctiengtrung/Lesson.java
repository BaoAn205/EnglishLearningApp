package com.example.apphoctiengtrung;

public class Lesson {
    private String title;
    private String hskLevel;
    private String description;
    private boolean isPremium;

    public Lesson(String title, String hskLevel, String description, boolean isPremium) {
        this.title = title;
        this.hskLevel = hskLevel;
        this.description = description;
        this.isPremium = isPremium;
    }

    public String getTitle() {
        return title;
    }

    public String getHskLevel() {
        return hskLevel;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPremium() {
        return isPremium;
    }
}
