package com.digigov.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    // Email validation: Required, valid format, unique
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Password validation: Required, minimum 8 characters
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long", max = 100)
    @Column(name = "password", nullable = false)
    private String password;

    // Phone validation: Required, exactly 11 digits, unique
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{11}$", message = "Phone number must be exactly 11 digits")
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // User → CitizenProfile: CASCADE DELETE ✅
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CitizenProfile citizenProfile;

    // User → Applications: CASCADE DELETE ✅
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;

    // User → Complaints: CASCADE DELETE ✅
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ComplaintFeedback> complaints;

    // Default constructor
    public User() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructor with parameters
    public User(String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdAt = LocalDateTime.now();
    }

    // All getters and setters remain the same...
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CitizenProfile getCitizenProfile() {
        return citizenProfile;
    }

    public void setCitizenProfile(CitizenProfile citizenProfile) {
        this.citizenProfile = citizenProfile;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<ComplaintFeedback> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<ComplaintFeedback> complaints) {
        this.complaints = complaints;
    }
}