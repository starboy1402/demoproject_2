package com.digigov.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaint_feedback")
public class ComplaintFeedback {
    
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;
    
    // User relationship: Required with explicit foreign key constraint
    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = @ForeignKey(
            name = "FK_COMPLAINT_FEEDBACK_USER",
            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE"
        )
    )
    private User user;
    
    // Feedback type validation: Required, enum values only
    @NotNull(message = "Feedback type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private FeedbackType type;
    
    // Subject validation: Required, max 50 characters
    @NotBlank(message = "Subject is required")
    @Size(max = 50, message = "Subject cannot exceed 50 characters", min = 1)
    @Column(name = "subject", nullable = false)
    private String subject;
    
    // Message validation: Required, max 100 characters
    @NotBlank(message = "Message is required")
    @Size(max = 100, message = "Message cannot exceed 100 characters", min = 1)
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;
    
    // Status: Simple text field, always "Submitted"
    @NotBlank(message = "Status is required")
    @Column(name = "status", nullable = false)
    private String status;
    
    // Submission date: Auto-generated, required
    @NotNull(message = "Submission date is required")
    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;
    
    // Default constructor
    public ComplaintFeedback() {
        this.submissionDate = LocalDateTime.now();
        this.status = "Submitted"; // Always "Submitted"
    }
    
    // Constructor with parameters
    public ComplaintFeedback(User user, FeedbackType type, String subject, String message) {
        this.user = user;
        this.type = type;
        this.subject = subject;
        this.message = message;
        this.submissionDate = LocalDateTime.now();
        this.status = "Submitted"; // Always "Submitted"
    }
    
    // Getters and setters
    public Long getFeedbackId() { return feedbackId; }
    public void setFeedbackId(Long feedbackId) { this.feedbackId = feedbackId; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public FeedbackType getType() { return type; }
    public void setType(FeedbackType type) { this.type = type; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }
}