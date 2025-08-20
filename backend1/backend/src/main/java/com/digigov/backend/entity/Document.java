package com.digigov.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document")
public class Document {
    
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;
    
    // Application relationship: Required with explicit foreign key constraint
    @NotNull(message = "Application is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false,
        foreignKey = @ForeignKey(
            name = "FK_DOCUMENT_APPLICATION",
            foreignKeyDefinition = "FOREIGN KEY (application_id) REFERENCES application(application_id) ON DELETE CASCADE ON UPDATE CASCADE"
        )
    )
    private Application application;
    
    // Document type validation: Required, enum values only
    @NotNull(message = "Document type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;
    
    // File name validation: Required, max 255 characters
    @NotBlank(message = "File name is required")
    @Size(max = 255, message = "File name cannot exceed 255 characters", min = 1)
    @Column(name = "file_name", nullable = false)
    private String fileName;
    
    // File path validation: Required, max 500 characters
    @NotBlank(message = "File path is required")
    @Size(max = 500, message = "File path cannot exceed 500 characters", min = 1)
    @Column(name = "file_path", nullable = false)
    private String filePath;
    
    // Upload timestamp: Auto-generated, required
    @NotNull(message = "Upload date is required")
    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;
    
    // Default constructor
    public Document() {
        this.uploadedAt = LocalDateTime.now();
    }
    
    // Constructor with parameters
    public Document(Application application, DocumentType documentType, String fileName, String filePath) {
        this.application = application;
        this.documentType = documentType;
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadedAt = LocalDateTime.now();
    }
    
    // Getters and setters
    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }
    
    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
    
    public DocumentType getDocumentType() { return documentType; }
    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}