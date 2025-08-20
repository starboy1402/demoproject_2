package com.digigov.backend.entity;

// Enum for Religion dropdown values
public enum Religion {
    ISLAM("Islam"),
    HINDUISM("Hinduism"), 
    BUDDHISM("Buddhism"),
    CHRISTIANITY("Christianity"),
    OTHER("Other");
    
    private final String displayName;
    
    Religion(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}