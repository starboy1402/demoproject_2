package com.digigov.backend.entity;

// Enum for feedback type dropdown values
public enum FeedbackType {
    COMPLAINT("Complaint"),
    SUGGESTION("Suggestion"),
    INQUIRY("General Inquiry");
    
    private final String displayName;
    
    FeedbackType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}