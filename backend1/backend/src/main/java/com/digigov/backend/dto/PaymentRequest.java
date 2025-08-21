package com.digigov.backend.dto;

import jakarta.validation.constraints.*;

public class PaymentRequest {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotBlank(message = "Transaction ID is required")
    @Size(min = 5, max = 50, message = "Transaction ID must be between 5 and 50 characters")
    private String transactionId;

    @NotBlank(message = "Payment method is required")
    @Pattern(regexp = "^(bKash|Nagad|Rocket)$", message = "Payment method must be bKash, Nagad, or Rocket")
    private String paymentMethod;

    private String phoneNumber; // Optional - phone number used for payment

    // Default constructor
    public PaymentRequest() {
    }

    // Constructor with parameters
    public PaymentRequest(Long applicationId, String transactionId, String paymentMethod) {
        this.applicationId = applicationId;
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
    }

    // Getters and setters
    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "applicationId=" + applicationId +
                ", transactionId='" + transactionId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
