package com.digigov.backend.repository;

import com.digigov.backend.entity.ComplaintFeedback;
import com.digigov.backend.entity.FeedbackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComplaintFeedbackRepository extends JpaRepository<ComplaintFeedback, Long> {

    // Find complaints/feedback by user ID
    List<ComplaintFeedback> findByUser_UserIdOrderBySubmissionDateDesc(Long userId);

    // Find by feedback type (using correct field name 'type')
    List<ComplaintFeedback> findByTypeOrderBySubmissionDateDesc(FeedbackType type);

    // Find by subject (partial match)
    List<ComplaintFeedback> findBySubjectContainingIgnoreCaseOrderBySubmissionDateDesc(String subject);

    // Find feedback submitted between dates
    List<ComplaintFeedback> findBySubmissionDateBetweenOrderBySubmissionDateDesc(LocalDateTime startDate,
            LocalDateTime endDate);

    // Count feedback by type (using correct field name 'type')
    long countByType(FeedbackType type);

    // Count total feedback for a user
    long countByUser_UserId(Long userId);
}
