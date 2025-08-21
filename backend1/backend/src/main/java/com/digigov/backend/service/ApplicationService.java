package com.digigov.backend.service;

import com.digigov.backend.entity.*;
import com.digigov.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CitizenProfileRepository citizenProfileRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // === APPLICATION SUBMISSION ===

    /**
     * Create and save a new application
     */
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    /**
     * Submit a new application for a government service
     */
    public Application submitApplication(Long userId, Long serviceId) {
        // Get user and validate
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Get citizen profile
        CitizenProfile citizenProfile = citizenProfileRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Citizen profile not found for user: " + userId));

        // Get service and validate
        com.digigov.backend.entity.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + serviceId));

        // Create new application
        Application application = new Application(citizenProfile, user, service);
        application.setSubmissionDate(LocalDateTime.now());
        application.setStatus(ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    // === APPLICATION RETRIEVAL ===

    /**
     * Get all applications
     */
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    /**
     * Get application by ID
     */
    public Optional<Application> getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId);
    }

    /**
     * Get applications by user ID
     */
    public List<Application> getApplicationsByUserId(Long userId) {
        return applicationRepository.findByUser_UserIdOrderBySubmissionDateDesc(userId);
    }

    /**
     * Get applications by status
     */
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatusOrderBySubmissionDateDesc(status);
    }

    /**
     * Get applications by service ID
     */
    public List<Application> getApplicationsByServiceId(Long serviceId) {
        return applicationRepository.findByService_ServiceIdOrderBySubmissionDateDesc(serviceId);
    }

    // === APPLICATION MANAGEMENT ===

    /**
     * Approve application
     */
    public Application approveApplication(Long applicationId, Long adminId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));

        application.setStatus(ApplicationStatus.APPROVED);
        // Note: Admin relationship can be added when Admin entity is updated

        // Mark payment as COMPLETED when application is approved
        // Autowire PaymentService or PaymentRepository as needed
        // If multiple payments per application, update all to COMPLETED
        Optional<Payment> paymentOpt = paymentRepository.findByApplication_ApplicationId(applicationId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            if (payment.getStatus() == PaymentStatus.PENDING) {
                payment.setStatus(PaymentStatus.COMPLETED);
                payment.setPaymentDate(java.time.LocalDateTime.now());
                paymentRepository.save(payment);
            }
        }

        return applicationRepository.save(application);
    }

    /**
     * Reject application
     */
    public Application rejectApplication(Long applicationId, Long adminId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));

        application.setStatus(ApplicationStatus.REJECTED);
        // Note: Admin relationship can be added when Admin entity is updated

        return applicationRepository.save(application);
    }

    // === STATISTICS ===

    /**
     * Get total application count
     */
    public long getTotalApplicationsCount() {
        return applicationRepository.count();
    }

    /**
     * Get application count by status
     */
    public long getApplicationCountByStatus(ApplicationStatus status) {
        return applicationRepository.countByStatus(status);
    }

    /**
     * Get total application count for user
     */
    public long getApplicationCountByUserId(Long userId) {
        return applicationRepository.countByUser_UserId(userId);
    }

    /**
     * Get pending applications older than days
     */
    public List<Application> getPendingApplicationsOlderThan(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return applicationRepository.findByStatusAndSubmissionDateBeforeOrderBySubmissionDateAsc(
                ApplicationStatus.PENDING, cutoffDate);
    }

    // === APPLICATION DELETION ===

    /**
     * Delete application (admin only)
     */
    public void deleteApplication(Long applicationId) {
        if (!applicationRepository.existsById(applicationId)) {
            throw new IllegalArgumentException("Application not found: " + applicationId);
        }
        applicationRepository.deleteById(applicationId);
    }
}
