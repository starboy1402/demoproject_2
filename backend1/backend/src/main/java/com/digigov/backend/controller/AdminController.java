package com.digigov.backend.controller;

import com.digigov.backend.entity.Admin;
import com.digigov.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // === ADMIN REGISTRATION ===

    /**
     * Register a new admin
     * POST /api/admin/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            Admin admin = adminService.registerAdmin(username, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(admin);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Registration failed"));
        }
    }

    // === ADMIN AUTHENTICATION ===

    /**
     * Authenticate admin login
     * POST /api/admin/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            Optional<Admin> adminOpt = adminService.authenticateByUsername(username, password);
            if (adminOpt.isPresent()) {
                return ResponseEntity.ok(adminOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid username or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Login failed"));
        }
    }

    // === ADMIN RETRIEVAL ===

    /**
     * Get all admins
     * GET /api/admin/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        try {
            List<Admin> admins = adminService.getAllAdmins();
            return ResponseEntity.ok(admins);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get admin by ID
     * GET /api/admin/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable Long id) {
        try {
            Optional<Admin> adminOpt = adminService.getAdminById(id);
            if (adminOpt.isPresent()) {
                return ResponseEntity.ok(adminOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve admin"));
        }
    }

    /**
     * Get admin by username
     * GET /api/admin/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getAdminByUsername(@PathVariable String username) {
        try {
            Optional<Admin> adminOpt = adminService.getAdminByUsername(username);
            if (adminOpt.isPresent()) {
                return ResponseEntity.ok(adminOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve admin"));
        }
    }

    // === ADMIN UPDATES ===

    /**
     * Update admin password
     * PUT /api/admin/{id}/password
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<?> updateAdminPassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String newPassword = request.get("password");
            adminService.updateAdminPassword(id, newPassword);
            return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update password"));
        }
    }

    // === ADMIN DELETION ===

    /**
     * Delete admin
     * DELETE /api/admin/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok(Map.of("message", "Admin deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete admin"));
        }
    }

    // === STATISTICS ===

    /**
     * Get total admin count
     * GET /api/admin/count
     */
    @GetMapping("/count")
    public ResponseEntity<?> getAdminCount() {
        try {
            long count = adminService.getTotalAdminCount();
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get admin count"));
        }
    }
}
