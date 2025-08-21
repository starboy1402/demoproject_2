package com.digigov.backend.repository;

import com.digigov.backend.entity.CitizenProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenProfileRepository extends JpaRepository<CitizenProfile, Long> {

    // Find citizen profile by user ID
    Optional<CitizenProfile> findByUser_UserId(Long userId);

    // Find citizen profile by NID number
    Optional<CitizenProfile> findByNidNumber(String nidNumber);

    // Check if NID number exists
    boolean existsByNidNumber(String nidNumber);

    // Find citizen profile by name
    Optional<CitizenProfile> findByName(String name);
}
