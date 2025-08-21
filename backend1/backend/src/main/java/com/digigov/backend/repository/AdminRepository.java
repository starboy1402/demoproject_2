package com.digigov.backend.repository;

import com.digigov.backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Find admin by username
    Optional<Admin> findByUsername(String username);

    // Check if username exists
    boolean existsByUsername(String username);
}
