package com.digigov.backend.entity;

// Enum for Payment Method dropdown values
public enum PaymentMethod {
    BKASH("bKash"),
    NAGAD("Nagad"), 
    ROCKET("Rocket");
    
    private final String displayName;
    
    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}