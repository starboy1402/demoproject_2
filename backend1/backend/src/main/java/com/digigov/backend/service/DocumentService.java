package com.digigov.backend.service;

import com.digigov.backend.entity.*;
import com.digigov.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    // === DOCUMENT UPLOAD ===

    /**
     * Upload a new document for an application
     */
    public Document uploadDocument(Long applicationId, String fileName, String filePath, DocumentType documentType) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));

        Document document = new Document();
        document.setApplication(application);
        document.setFileName(fileName);
        document.setFilePath(filePath);
        document.setDocumentType(documentType);
        document.setUploadedAt(LocalDateTime.now());

        return documentRepository.save(document);
    }

    // === DOCUMENT RETRIEVAL ===

    /**
     * Get all documents
     */
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    /**
     * Get document by ID
     */
    public Optional<Document> getDocumentById(Long documentId) {
        return documentRepository.findById(documentId);
    }

    /**
     * Get documents by application ID
     */
    public List<Document> getDocumentsByApplicationId(Long applicationId) {
        return documentRepository.findByApplication_ApplicationIdOrderByUploadedAtDesc(applicationId);
    }

    /**
     * Get documents by document type
     */
    public List<Document> getDocumentsByType(DocumentType documentType) {
        return documentRepository.findByDocumentTypeOrderByUploadedAtDesc(documentType);
    }

    /**
     * Get documents by user ID
     */
    public List<Document> getDocumentsByUserId(Long userId) {
        return documentRepository.findByApplication_User_UserIdOrderByUploadedAtDesc(userId);
    }

    /**
     * Search documents by filename
     */
    public List<Document> searchDocumentsByFileName(String fileName) {
        return documentRepository.findByFileNameContainingIgnoreCaseOrderByUploadedAtDesc(fileName);
    }

    // === DOCUMENT MANAGEMENT ===

    /**
     * Update document file path
     */
    public Document updateDocumentPath(Long documentId, String newFilePath) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found: " + documentId));

        document.setFilePath(newFilePath);
        return documentRepository.save(document);
    }

    /**
     * Delete document
     */
    public void deleteDocument(Long documentId) {
        if (!documentRepository.existsById(documentId)) {
            throw new IllegalArgumentException("Document not found: " + documentId);
        }
        documentRepository.deleteById(documentId);
    }

    // === STATISTICS ===

    /**
     * Get document count by application
     */
    public long getDocumentCountByApplication(Long applicationId) {
        return documentRepository.countByApplication_ApplicationId(applicationId);
    }

    /**
     * Get document count by type
     */
    public long getDocumentCountByType(DocumentType documentType) {
        return documentRepository.countByDocumentType(documentType);
    }
}
