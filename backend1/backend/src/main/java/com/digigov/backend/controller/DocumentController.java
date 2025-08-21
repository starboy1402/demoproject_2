package com.digigov.backend.controller;

import com.digigov.backend.entity.Document;
import com.digigov.backend.entity.DocumentType;
import com.digigov.backend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/document")
@CrossOrigin(origins = "*")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // === DOCUMENT UPLOAD ===

    /**
     * Upload a new document
     * POST /api/document/upload
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(@RequestBody Map<String, Object> request) {
        try {
            Long applicationId = Long.valueOf(request.get("applicationId").toString());
            String fileName = request.get("fileName").toString();
            String filePath = request.get("filePath").toString();
            DocumentType documentType = DocumentType.valueOf(request.get("documentType").toString().toUpperCase());

            Document document = documentService.uploadDocument(applicationId, fileName, filePath, documentType);
            return ResponseEntity.status(HttpStatus.CREATED).body(document);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload document"));
        }
    }

    // === DOCUMENT RETRIEVAL ===

    /**
     * Get all documents
     * GET /api/document/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<Document>> getAllDocuments() {
        try {
            List<Document> documents = documentService.getAllDocuments();
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get document by ID
     * GET /api/document/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentById(@PathVariable Long id) {
        try {
            Optional<Document> documentOpt = documentService.getDocumentById(id);
            if (documentOpt.isPresent()) {
                return ResponseEntity.ok(documentOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve document"));
        }
    }

    /**
     * Get documents by application ID
     * GET /api/document/application/{applicationId}
     */
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<Document>> getDocumentsByApplicationId(@PathVariable Long applicationId) {
        try {
            List<Document> documents = documentService.getDocumentsByApplicationId(applicationId);
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get documents by document type
     * GET /api/document/type/{type}
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Document>> getDocumentsByType(@PathVariable String type) {
        try {
            DocumentType documentType = DocumentType.valueOf(type.toUpperCase());
            List<Document> documents = documentService.getDocumentsByType(documentType);
            return ResponseEntity.ok(documents);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get documents by user ID
     * GET /api/document/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Document>> getDocumentsByUserId(@PathVariable Long userId) {
        try {
            List<Document> documents = documentService.getDocumentsByUserId(userId);
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Search documents by filename
     * GET /api/document/search?filename={filename}
     */
    @GetMapping("/search")
    public ResponseEntity<List<Document>> searchDocumentsByFileName(@RequestParam String filename) {
        try {
            List<Document> documents = documentService.searchDocumentsByFileName(filename);
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // === DOCUMENT MANAGEMENT ===

    /**
     * Update document path
     * PUT /api/document/{id}/path
     */
    @PutMapping("/{id}/path")
    public ResponseEntity<?> updateDocumentPath(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String newFilePath = request.get("filePath");
            Document document = documentService.updateDocumentPath(id, newFilePath);
            return ResponseEntity.ok(document);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update document path"));
        }
    }

    /**
     * Delete document
     * DELETE /api/document/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok(Map.of("message", "Document deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete document"));
        }
    }

    // === STATISTICS ===

    /**
     * Get document count by application
     * GET /api/document/count/application/{applicationId}
     */
    @GetMapping("/count/application/{applicationId}")
    public ResponseEntity<?> getDocumentCountByApplication(@PathVariable Long applicationId) {
        try {
            long count = documentService.getDocumentCountByApplication(applicationId);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get document count"));
        }
    }

    /**
     * Get document count by type
     * GET /api/document/count/type/{type}
     */
    @GetMapping("/count/type/{type}")
    public ResponseEntity<?> getDocumentCountByType(@PathVariable String type) {
        try {
            DocumentType documentType = DocumentType.valueOf(type.toUpperCase());
            long count = documentService.getDocumentCountByType(documentType);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid document type"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get document count"));
        }
    }
}
