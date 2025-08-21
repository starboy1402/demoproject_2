package com.digigov.backend.controller;

import com.digigov.backend.entity.CitizenProfile;
import com.digigov.backend.entity.Gender;
import com.digigov.backend.entity.Religion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/citizen")
@CrossOrigin(origins = "*")
public class CitizenController {

    // Check if user has completed CitizenProfile
    @GetMapping("/profile/check/{userId}")
    public ResponseEntity<Map<String, Object>> checkCitizenProfile(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            // For Phase 2.5 testing, using mock data
            // In real implementation, this would check the database
            boolean hasProfile = false; // Default to false to force profile completion

            // Mock logic: user with ID 1 has profile, others don't
            if (userId == 1L) {
                hasProfile = true;
            }

            response.put("hasProfile", hasProfile);
            response.put("message", hasProfile ? "Profile exists" : "Profile not found");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("hasProfile", false);
            response.put("message", "Error checking profile: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Create or update CitizenProfile
    @PostMapping("/profile")
    public ResponseEntity<Map<String, Object>> createOrUpdateProfile(@RequestBody CitizenProfile citizenProfile) {
        Map<String, Object> response = new HashMap<>();

        try {
            // For Phase 2.5 testing, return success without database operation
            response.put("success", true);
            response.put("message", "Profile saved successfully");
            response.put("profile", citizenProfile);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error saving profile: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Get CitizenProfile by user ID
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getCitizenProfile(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            // For Phase 2.5 testing, return mock data
            CitizenProfile mockProfile = new CitizenProfile();
            mockProfile.setProfileId(1L);
            mockProfile.setName("John Doe");
            mockProfile.setNidNumber("1234567890123");
            mockProfile.setCurrentAddress("123 Main Street, Dhaka");
            mockProfile.setPermanentAddress("123 Main Street, Dhaka");
            mockProfile.setDateOfBirth(LocalDate.of(1990, 1, 1));
            mockProfile.setGender(Gender.MALE);
            mockProfile.setFathersName("John Doe Sr.");
            mockProfile.setMothersName("Jane Doe");
            mockProfile.setReligion(Religion.ISLAM);
            mockProfile.setProfession("Engineer");

            response.put("success", true);
            response.put("profile", mockProfile);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving profile: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
