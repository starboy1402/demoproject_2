package com.digigov.backend.controller;

import com.digigov.backend.entity.CitizenProfile;
import com.digigov.backend.entity.Gender;
import com.digigov.backend.entity.Religion;
import com.digigov.backend.repository.CitizenProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/citizen")
@CrossOrigin(origins = "*")
public class CitizenController {

    @Autowired
    private CitizenProfileRepository citizenProfileRepository;

    // Check if user has completed CitizenProfile
    @GetMapping("/profile/check/{userId}")
    public ResponseEntity<Map<String, Object>> checkCitizenProfile(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Check the database for actual profile
            Optional<CitizenProfile> profileOpt = citizenProfileRepository.findByUser_UserId(userId);
            boolean hasProfile = profileOpt.isPresent();

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
            // Validate required fields
            if (citizenProfile.getUser() == null || citizenProfile.getUser().getUserId() == null) {
                response.put("success", false);
                response.put("message", "User ID is required");
                return ResponseEntity.badRequest().body(response);
            }

            // Save or update the profile in the database
            CitizenProfile savedProfile = citizenProfileRepository.save(citizenProfile);
            
            response.put("success", true);
            response.put("message", "Profile saved successfully");
            response.put("profile", savedProfile);

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
            // Retrieve actual profile from database
            Optional<CitizenProfile> profileOpt = citizenProfileRepository.findByUser_UserId(userId);
            
            if (profileOpt.isPresent()) {
                response.put("success", true);
                response.put("profile", profileOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Profile not found for user ID: " + userId);
                return ResponseEntity.status(404).body(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving profile: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
