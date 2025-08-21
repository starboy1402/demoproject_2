package com.digigov.backend.dto;

import jakarta.validation.constraints.*;
import java.util.Map;

public class ApplicationSubmissionRequest {

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotBlank(message = "Purpose is required")
    @Size(max = 200, message = "Purpose cannot exceed 200 characters")
    private String purpose;

    @Size(max = 500, message = "Additional information cannot exceed 500 characters")
    private String additionalInfo;

    // For storing any additional form data specific to the service type
    private Map<String, Object> formData;

    // Default constructor
    public ApplicationSubmissionRequest() {
    }

    // Constructor with parameters
    public ApplicationSubmissionRequest(Long serviceId, String purpose) {
        this.serviceId = serviceId;
        this.purpose = purpose;
    }

    // Getters and setters
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Map<String, Object> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, Object> formData) {
        this.formData = formData;
    }

    @Override
    public String toString() {
        return "ApplicationSubmissionRequest{" +
                "serviceId=" + serviceId +
                ", purpose='" + purpose + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", formData=" + formData +
                '}';
    }
}
