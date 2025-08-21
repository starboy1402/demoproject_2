package com.digigov.backend.repository;

import com.digigov.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find user by phone
    Optional<User> findByPhone(String phone);

    // Check if phone exists
    boolean existsByPhone(String phone);
}
