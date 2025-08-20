package com.digigov.backend.entity;

// Enum for payment status
public enum PaymentStatus {
    PENDING,    // Payment initiated but not confirmed
    COMPLETED,  // Payment successfully completed
    FAILED      // Payment failed or rejected
}