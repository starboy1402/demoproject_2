package com.digigov.backend.service;

import com.digigov.backend.entity.Admin;
import com.digigov.backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // === ADMIN REGISTRATION ===

    /**
     * Register a new admin
     */
    public Admin registerAdmin(String username, String password) {
        // Check if username already exists
        if (adminRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }

        // Create new admin
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password); // In real app, hash this password
        admin.setCreatedAt(LocalDateTime.now());

        return adminRepository.save(admin);
    }

    // === ADMIN AUTHENTICATION ===

    /**
     * Authenticate admin by username and password
     */
    public Optional<Admin> authenticateByUsername(String username, String password) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            // In real app, compare hashed passwords
            if (admin.getPassword().equals(password)) {
                return Optional.of(admin);
            }
        }

        return Optional.empty();
    }

    // === ADMIN RETRIEVAL ===

    /**
     * Get all admins
     */
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    /**
     * Get admin by ID
     */
    public Optional<Admin> getAdminById(Long adminId) {
        return adminRepository.findById(adminId);
    }

    /**
     * Get admin by username
     */
    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    // === ADMIN UPDATES ===

    /**
     * Update admin password
     */
    public Admin updateAdminPassword(Long adminId, String newPassword) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found: " + adminId));

        admin.setPassword(newPassword); // In real app, hash this password
        return adminRepository.save(admin);
    }

    // === ADMIN DELETION ===

    /**
     * Delete admin by ID
     */
    public void deleteAdmin(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new IllegalArgumentException("Admin not found: " + adminId);
        }
        adminRepository.deleteById(adminId);
    }

    // === UTILITY METHODS ===

    /**
     * Check if username exists
     */
    public boolean usernameExists(String username) {
        return adminRepository.existsByUsername(username);
    }

    /**
     * Get total admin count
     */
    public long getTotalAdminCount() {
        return adminRepository.count();
    }
}
