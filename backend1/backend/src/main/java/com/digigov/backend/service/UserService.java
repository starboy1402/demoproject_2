package com.digigov.backend.service;

import com.digigov.backend.entity.User;
import com.digigov.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // === USER REGISTRATION ===

    /**
     * Register a new user
     */
    public User registerUser(String email, String password, String phone) {
        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        // Check if phone already exists
        if (userRepository.existsByPhone(phone)) {
            throw new IllegalArgumentException("Phone number already exists: " + phone);
        }

        // Create new user
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Hash the password
        user.setPhone(phone);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // === USER AUTHENTICATION ===

    /**
     * Authenticate user by email and password
     */
    public Optional<User> authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Compare hashed passwords
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    // === USER RETRIEVAL ===

    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user by ID
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Get user by email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Get user by phone
     */
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    // === USER UPDATES ===

    /**
     * Update user email
     */
    public User updateUserEmail(Long userId, String newEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Check if new email already exists
        if (!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("Email already exists: " + newEmail);
        }

        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    /**
     * Update user phone
     */
    public User updateUserPhone(Long userId, String newPhone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Check if new phone already exists
        if (!user.getPhone().equals(newPhone) && userRepository.existsByPhone(newPhone)) {
            throw new IllegalArgumentException("Phone number already exists: " + newPhone);
        }

        user.setPhone(newPhone);
        return userRepository.save(user);
    }

    /**
     * Update user password
     */
    public User updateUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        user.setPassword(passwordEncoder.encode(newPassword)); // Hash the password
        return userRepository.save(user);
    }

    // === USER DELETION ===

    /**
     * Delete user by ID
     */
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        userRepository.deleteById(userId);
    }

    // === UTILITY METHODS ===

    /**
     * Check if email exists
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Check if phone exists
     */
    public boolean phoneExists(String phone) {
        return userRepository.existsByPhone(phone);
    }

    /**
     * Get total user count
     */
    public long getTotalUserCount() {
        return userRepository.count();
    }
}
