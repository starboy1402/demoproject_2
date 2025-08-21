package com.digigov.backend.repository;

import com.digigov.backend.entity.Application;
import com.digigov.backend.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Find applications by user ID
    List<Application> findByUser_UserIdOrderBySubmissionDateDesc(Long userId);

    // Find applications by citizen profile ID
    List<Application> findByCitizenProfile_ProfileIdOrderBySubmissionDateDesc(Long profileId);

    // Find applications by status
    List<Application> findByStatusOrderBySubmissionDateDesc(ApplicationStatus status);

    // Find applications by service ID
    List<Application> findByService_ServiceIdOrderBySubmissionDateDesc(Long serviceId);

    // Find applications by status and service
    List<Application> findByStatusAndService_ServiceIdOrderBySubmissionDateDesc(ApplicationStatus status,
            Long serviceId);

    // Find applications submitted between dates
    List<Application> findBySubmissionDateBetweenOrderBySubmissionDateDesc(LocalDateTime startDate,
            LocalDateTime endDate);

    // Count applications by status
    long countByStatus(ApplicationStatus status);

    // Count total applications for a user
    long countByUser_UserId(Long userId);

    // Find pending applications older than specific date
    List<Application> findByStatusAndSubmissionDateBeforeOrderBySubmissionDateAsc(ApplicationStatus status,
            LocalDateTime beforeDate);
}
