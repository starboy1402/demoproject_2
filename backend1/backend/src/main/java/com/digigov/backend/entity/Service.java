package com.digigov.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "service")
public class Service {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;
    
    // Service name validation: Required, max 100 characters, unique
    @NotBlank(message = "Service name is required")
    @Size(max = 100, message = "Service name cannot exceed 100 characters", min = 1)
    @Column(name = "service_name", nullable = false, unique = true)
    private String serviceName;
    
    // Fee validation: Required, must be >= 0
    @NotNull(message = "Fee is required")
    @DecimalMin(value = "0.0", message = "Fee cannot be negative")
    @Column(name = "fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal fee;
    
    // Description validation: Optional, max 1000 characters
    @Size(max = 1000, message = "Description cannot exceed 1000 characters", min = 1)
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    // Service → Applications: Database prevents deletion if applications exist
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Application> applications;
    
    // Default constructor
    public Service() {}
    
    // Constructor with parameters
    public Service(String serviceName, BigDecimal fee, String description) {
        this.serviceName = serviceName;
        this.fee = fee;
        this.description = description;
    }
    
    // Getters and setters
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
    
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public BigDecimal getFee() { return fee; }
    public void setFee(BigDecimal fee) { this.fee = fee; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }
}