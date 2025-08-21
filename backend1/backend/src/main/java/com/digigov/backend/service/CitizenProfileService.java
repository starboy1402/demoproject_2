package com.digigov.backend.service;

import com.digigov.backend.entity.CitizenProfile;
import com.digigov.backend.repository.CitizenProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CitizenProfileService {

    @Autowired
    private CitizenProfileRepository citizenProfileRepository;

    public boolean hasProfile(Long userId) {
        // Check if user has completed CitizenProfile
        return citizenProfileRepository.findByUser_UserId(userId).isPresent();
    }

    public CitizenProfile createOrUpdateProfile(CitizenProfile citizenProfile) {
        return citizenProfileRepository.save(citizenProfile);
    }

    public Optional<CitizenProfile> getProfileByUserId(Long userId) {
        return citizenProfileRepository.findByUser_UserId(userId);
    }

    public void deleteProfile(Long id) {
        citizenProfileRepository.deleteById(id);
    }
}
