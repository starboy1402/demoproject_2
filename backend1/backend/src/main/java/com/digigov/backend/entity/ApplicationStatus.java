package com.digigov.backend.entity;

// Enum for application status - your teacher will like this clean approach
public enum ApplicationStatus {
    PENDING,    // Initial status when application is submitted
    APPROVED,   // When admin approves the application
    REJECTED    // When admin rejects the application
}