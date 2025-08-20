package com.digigov.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "application")
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;
    
    // 1. CitizenProfile relationship: Required with explicit foreign key
    @NotNull(message = "Citizen profile is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false,
        foreignKey = @ForeignKey(
            name = "FK_APPLICATION_CITIZEN_PROFILE",
            foreignKeyDefinition = "FOREIGN KEY (profile_id) REFERENCES citizen_profile(profile_id) ON DELETE CASCADE ON UPDATE CASCADE"
        )
    )
    private CitizenProfile citizenProfile;
    
    // 2. User relationship: Required with explicit foreign key
    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = @ForeignKey(
            name = "FK_APPLICATION_USER",
            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE ON UPDATE CASCADE"
        )
    )
    private User user;
    
    // 3. Service relationship: Required with explicit foreign key
    @NotNull(message = "Service is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false,
        foreignKey = @ForeignKey(
            name = "FK_APPLICATION_SERVICE",
            foreignKeyDefinition = "FOREIGN KEY (service_id) REFERENCES service(service_id) ON DELETE RESTRICT ON UPDATE CASCADE"
        )
    )
    private Service service;
    
    // 4. Admin relationship: Optional with explicit foreign key (SET NULL when admin deleted)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id",
        foreignKey = @ForeignKey(
            name = "FK_APPLICATION_ADMIN",
            foreignKeyDefinition = "FOREIGN KEY (admin_id) REFERENCES admin(admin_id) ON DELETE SET NULL ON UPDATE CASCADE"
        )
    )
    private Admin admin;
    
    // Submission date: Auto-generated, required
    @NotNull(message = "Submission date is required")
    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;
    
    // Status: Required, defaults to PENDING
    @NotNull(message = "Application status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;
    
    // 5. Payment relationship: One Application → Many Payments (CASCADE DELETE)
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;
    
    // 6. Document relationship: One Application → Many Documents (CASCADE DELETE)
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;
    
    // Default constructor
    public Application() {
        this.submissionDate = LocalDateTime.now();
        this.status = ApplicationStatus.PENDING;
    }
    
    // Constructor with required relationships
    public Application(CitizenProfile citizenProfile, User user, Service service) {
        this.citizenProfile = citizenProfile;
        this.user = user;
        this.service = service;
        this.submissionDate = LocalDateTime.now();
        this.status = ApplicationStatus.PENDING;
    }
    
    // Getters and setters
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    
    public CitizenProfile getCitizenProfile() { return citizenProfile; }
    public void setCitizenProfile(CitizenProfile citizenProfile) { this.citizenProfile = citizenProfile; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }
    
    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }
    
    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }
    
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    
    public List<Payment> getPayments() { return payments; }
    public void setPayments(List<Payment> payments) { this.payments = payments; }
    
    public List<Document> getDocuments() { return documents; }
    public void setDocuments(List<Document> documents) { this.documents = documents; }
}