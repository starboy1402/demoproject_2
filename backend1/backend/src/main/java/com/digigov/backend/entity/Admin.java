package com.digigov.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "admin")
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;
    
    // Username validation: Required, unique, 3-50 characters, alphanumeric + underscore
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    // Password validation: Required, minimum 8 characters
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long", max = 100)
    @Column(name = "password", nullable = false)
    private String password;
    
    // Created date: Auto-generated, required
    @NotNull(message = "Created date is required")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    // Admin → Applications: SET NULL when admin deleted (applications remain unassigned)
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Application> applications;
    
    // Default constructor
    public Admin() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor with parameters
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and setters
    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }
}