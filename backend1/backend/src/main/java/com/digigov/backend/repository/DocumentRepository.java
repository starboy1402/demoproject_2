package com.digigov.backend.repository;

import com.digigov.backend.entity.Document;
import com.digigov.backend.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    // Find documents by application ID (using correct field name 'uploadedAt')
    List<Document> findByApplication_ApplicationIdOrderByUploadedAtDesc(Long applicationId);

    // Find documents by document type (using correct field name 'uploadedAt')
    List<Document> findByDocumentTypeOrderByUploadedAtDesc(DocumentType documentType);

    // Find documents by user ID through application (using correct field name
    // 'uploadedAt')
    List<Document> findByApplication_User_UserIdOrderByUploadedAtDesc(Long userId);

    // Find documents by file name (partial match, using correct field name
    // 'uploadedAt')
    List<Document> findByFileNameContainingIgnoreCaseOrderByUploadedAtDesc(String fileName);

    // Find documents uploaded between dates (using correct field name 'uploadedAt')
    List<Document> findByUploadedAtBetweenOrderByUploadedAtDesc(LocalDateTime startDate, LocalDateTime endDate);

    // Count documents by application
    long countByApplication_ApplicationId(Long applicationId);

    // Count documents by type
    long countByDocumentType(DocumentType documentType);
}
