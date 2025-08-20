package com.digigov.backend.entity;

// Enum for required document types
public enum DocumentType {
    NID_COPY("NID Copy"),
    PASSPORT_PHOTO("Passport Size Photo");
    
    private final String displayName;
    
    DocumentType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}