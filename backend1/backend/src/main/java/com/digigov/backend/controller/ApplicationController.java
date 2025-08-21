package com.digigov.backend.controller;

import com.digigov.backend.entity.Application;
import com.digigov.backend.entity.ApplicationStatus;
import com.digigov.backend.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // === APPLICATION SUBMISSION ===

    /**
     * Submit a new application
     * POST /api/application/submit
     */
    @PostMapping("/submit")
    public ResponseEntity<?> submitApplication(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            Long serviceId = Long.valueOf(request.get("serviceId").toString());

            Application application = applicationService.submitApplication(userId, serviceId);
            return ResponseEntity.status(HttpStatus.CREATED).body(application);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to submit application"));
        }
    }

    // === APPLICATION RETRIEVAL ===

    /**
     * Get all applications
     * GET /api/application/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllApplications() {
        try {
            // TEMPORARY: Return mock applications for testing
            List<Map<String, Object>> mockApplications = List.of(
                    Map.of(
                            "id", 1L, // Changed from "applicationId" to "id"
                            "applicantName", "John Doe",
                            "serviceType", "Birth Certificate",
                            "status", "PENDING",
                            "submissionDate", "2025-08-20T10:30:00",
                            "phoneNumber", "+1234567890",
                            "email", "john.doe@email.com"),
                    Map.of(
                            "id", 2L, // Changed from "applicationId" to "id"
                            "applicantName", "Jane Smith",
                            "serviceType", "Marriage Certificate",
                            "status", "PENDING",
                            "submissionDate", "2025-08-19T14:15:00",
                            "phoneNumber", "+1234567891",
                            "email", "jane.smith@email.com"),
                    Map.of(
                            "id", 3L, // Changed from "applicationId" to "id"
                            "applicantName", "Mike Johnson",
                            "serviceType", "Death Certificate",
                            "status", "APPROVED",
                            "submissionDate", "2025-08-18T09:45:00",
                            "phoneNumber", "+1234567892",
                            "email", "mike.johnson@email.com"));

            return ResponseEntity.ok(mockApplications);

            /*
             * TODO: Replace with real database call once DB has data
             * List<Application> applications = applicationService.getAllApplications();
             * return ResponseEntity.ok(applications);
             */
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get application by ID
     * GET /api/application/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long id) {
        try {
            Optional<Application> applicationOpt = applicationService.getApplicationById(id);
            if (applicationOpt.isPresent()) {
                return ResponseEntity.ok(applicationOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve application"));
        }
    }

    /**
     * Get applications by user ID
     * GET /api/application/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getApplicationsByUserId(@PathVariable Long userId) {
        try {
            List<Application> applications = applicationService.getApplicationsByUserId(userId);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get applications by status
     * GET /api/application/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(@PathVariable String status) {
        try {
            ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
            List<Application> applications = applicationService.getApplicationsByStatus(applicationStatus);
            return ResponseEntity.ok(applications);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get applications by service ID
     * GET /api/application/service/{serviceId}
     */
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<Application>> getApplicationsByServiceId(@PathVariable Long serviceId) {
        try {
            List<Application> applications = applicationService.getApplicationsByServiceId(serviceId);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // === APPLICATION MANAGEMENT ===

    /**
     * Approve application
     * PUT /api/application/{id}/approve
     */
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveApplication(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            // TEMPORARY: Mock approval for testing
            Map<String, Object> response = Map.of(
                    "message", "Application approved successfully",
                    "applicationId", id,
                    "newStatus", "APPROVED",
                    "success", true);
            return ResponseEntity.ok(response);

            /*
             * TODO: Replace with real database call once DB is working
             * Long adminId = Long.valueOf(request.get("adminId").toString());
             * Application application = applicationService.approveApplication(id, adminId);
             * return ResponseEntity.ok(application);
             */
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to approve application"));
        }
    }

    /**
     * Reject application
     * PUT /api/application/{id}/reject
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectApplication(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            // TEMPORARY: Mock rejection for testing
            Map<String, Object> response = Map.of(
                    "message", "Application rejected successfully",
                    "applicationId", id,
                    "newStatus", "REJECTED",
                    "success", true);
            return ResponseEntity.ok(response);

            /*
             * TODO: Replace with real database call once DB is working
             * Long adminId = Long.valueOf(request.get("adminId").toString());
             * Application application = applicationService.rejectApplication(id, adminId);
             * return ResponseEntity.ok(application);
             */
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to reject application"));
        }
    }

    // === STATISTICS ===

    /**
     * Get all application statistics for admin dashboard
     * GET /api/application/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<?> getApplicationStatistics() {
        try {
            // MOCK DATA for testing - replace with real database calls later
            Map<String, Object> statistics = Map.of(
                    "totalApplications", 25,
                    "pendingApplications", 8,
                    "approvedApplications", 12,
                    "rejectedApplications", 5);

            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get application statistics"));
        }
    }

    /**
     * Get application count by status
     * GET /api/application/count/status/{status}
     */
    @GetMapping("/count/status/{status}")
    public ResponseEntity<?> getApplicationCountByStatus(@PathVariable String status) {
        try {
            ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
            long count = applicationService.getApplicationCountByStatus(applicationStatus);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid status"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get application count"));
        }
    }

    /**
     * Get application count by user
     * GET /api/application/count/user/{userId}
     */
    @GetMapping("/count/user/{userId}")
    public ResponseEntity<?> getApplicationCountByUserId(@PathVariable Long userId) {
        try {
            long count = applicationService.getApplicationCountByUserId(userId);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get application count"));
        }
    }
}
