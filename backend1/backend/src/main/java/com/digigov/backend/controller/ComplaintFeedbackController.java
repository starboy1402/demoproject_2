package com.digigov.backend.controller;

import com.digigov.backend.entity.ComplaintFeedback;
import com.digigov.backend.entity.FeedbackType;
import com.digigov.backend.service.ComplaintFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/complaint-feedback")
@CrossOrigin(origins = "*")
public class ComplaintFeedbackController {

    @Autowired
    private ComplaintFeedbackService complaintFeedbackService;

    // === COMPLAINT/FEEDBACK SUBMISSION ===

    /**
     * Submit a new complaint/feedback
     * POST /api/complaint-feedback/submit
     */
    @PostMapping("/submit")
    public ResponseEntity<?> submitComplaintFeedback(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            FeedbackType feedbackType = FeedbackType.valueOf(request.get("feedbackType").toString().toUpperCase());
            String subject = request.get("subject").toString();
            String message = request.get("message").toString();

            ComplaintFeedback complaintFeedback = complaintFeedbackService.submitComplaintFeedback(
                    userId, feedbackType, subject, message);
            return ResponseEntity.status(HttpStatus.CREATED).body(complaintFeedback);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to submit complaint/feedback"));
        }
    }

    /**
     * Submit anonymous complaint/feedback
     * POST /api/complaint-feedback/submit-anonymous
     */
    @PostMapping("/submit-anonymous")
    public ResponseEntity<?> submitAnonymousComplaintFeedback(@RequestBody Map<String, Object> request) {
        try {
            FeedbackType feedbackType = FeedbackType.valueOf(request.get("feedbackType").toString().toUpperCase());
            String subject = request.get("subject").toString();
            String message = request.get("message").toString();

            ComplaintFeedback complaintFeedback = complaintFeedbackService.submitAnonymousComplaintFeedback(
                    feedbackType, subject, message);
            return ResponseEntity.status(HttpStatus.CREATED).body(complaintFeedback);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to submit anonymous complaint/feedback"));
        }
    }

    // === COMPLAINT/FEEDBACK RETRIEVAL ===

    /**
     * Get all complaints/feedback
     * GET /api/complaint-feedback/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<ComplaintFeedback>> getAllComplaintsFeedback() {
        try {
            List<ComplaintFeedback> complaintsFeedback = complaintFeedbackService.getAllComplaintsFeedback();
            return ResponseEntity.ok(complaintsFeedback);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get complaint/feedback by ID
     * GET /api/complaint-feedback/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getComplaintFeedbackById(@PathVariable Long id) {
        try {
            Optional<ComplaintFeedback> complaintFeedbackOpt = complaintFeedbackService.getComplaintFeedbackById(id);
            if (complaintFeedbackOpt.isPresent()) {
                return ResponseEntity.ok(complaintFeedbackOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve complaint/feedback"));
        }
    }

    /**
     * Get complaints/feedback by user ID
     * GET /api/complaint-feedback/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ComplaintFeedback>> getComplaintsFeedbackByUserId(@PathVariable Long userId) {
        try {
            List<ComplaintFeedback> complaintsFeedback = complaintFeedbackService.getComplaintsFeedbackByUserId(userId);
            return ResponseEntity.ok(complaintsFeedback);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get complaints/feedback by type
     * GET /api/complaint-feedback/type/{type}
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ComplaintFeedback>> getComplaintsFeedbackByType(@PathVariable String type) {
        try {
            FeedbackType feedbackType = FeedbackType.valueOf(type.toUpperCase());
            List<ComplaintFeedback> complaintsFeedback = complaintFeedbackService
                    .getComplaintsFeedbackByType(feedbackType);
            return ResponseEntity.ok(complaintsFeedback);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Search complaints/feedback by subject
     * GET /api/complaint-feedback/search?subject={subject}
     */
    @GetMapping("/search")
    public ResponseEntity<List<ComplaintFeedback>> searchComplaintsFeedbackBySubject(@RequestParam String subject) {
        try {
            List<ComplaintFeedback> complaintsFeedback = complaintFeedbackService
                    .searchComplaintsFeedbackBySubject(subject);
            return ResponseEntity.ok(complaintsFeedback);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // === COMPLAINT/FEEDBACK MANAGEMENT ===

    /**
     * Update complaint/feedback message
     * PUT /api/complaint-feedback/{id}/message
     */
    @PutMapping("/{id}/message")
    public ResponseEntity<?> updateComplaintFeedbackMessage(@PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            String newMessage = request.get("message");
            ComplaintFeedback complaintFeedback = complaintFeedbackService.updateComplaintFeedbackMessage(id,
                    newMessage);
            return ResponseEntity.ok(complaintFeedback);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update complaint/feedback"));
        }
    }

    /**
     * Delete complaint/feedback
     * DELETE /api/complaint-feedback/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComplaintFeedback(@PathVariable Long id) {
        try {
            complaintFeedbackService.deleteComplaintFeedback(id);
            return ResponseEntity.ok(Map.of("message", "Complaint/Feedback deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete complaint/feedback"));
        }
    }

    // === STATISTICS ===

    /**
     * Get complaint/feedback count by type
     * GET /api/complaint-feedback/count/type/{type}
     */
    @GetMapping("/count/type/{type}")
    public ResponseEntity<?> getComplaintFeedbackCountByType(@PathVariable String type) {
        try {
            FeedbackType feedbackType = FeedbackType.valueOf(type.toUpperCase());
            long count = complaintFeedbackService.getComplaintFeedbackCountByType(feedbackType);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid feedback type"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get complaint/feedback count"));
        }
    }

    /**
     * Get complaint/feedback count by user
     * GET /api/complaint-feedback/count/user/{userId}
     */
    @GetMapping("/count/user/{userId}")
    public ResponseEntity<?> getComplaintFeedbackCountByUserId(@PathVariable Long userId) {
        try {
            long count = complaintFeedbackService.getComplaintFeedbackCountByUserId(userId);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get complaint/feedback count"));
        }
    }

    /**
     * Get total complaint/feedback count
     * GET /api/complaint-feedback/count/total
     */
    @GetMapping("/count/total")
    public ResponseEntity<?> getTotalComplaintFeedbackCount() {
        try {
            long count = complaintFeedbackService.getTotalComplaintFeedbackCount();
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get total complaint/feedback count"));
        }
    }
}
