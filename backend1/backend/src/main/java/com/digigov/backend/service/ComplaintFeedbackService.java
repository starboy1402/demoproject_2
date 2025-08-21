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
public class ComplaintFeedbackService {

    @Autowired
    private ComplaintFeedbackRepository complaintFeedbackRepository;

    @Autowired
    private UserRepository userRepository;

    // === COMPLAINT/FEEDBACK SUBMISSION ===

    /**
     * Submit a new complaint or feedback
     */
    public ComplaintFeedback submitComplaintFeedback(Long userId, FeedbackType feedbackType,
            String subject, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        ComplaintFeedback complaintFeedback = new ComplaintFeedback();
        complaintFeedback.setUser(user);
        complaintFeedback.setType(feedbackType);
        complaintFeedback.setSubject(subject);
        complaintFeedback.setMessage(message);
        complaintFeedback.setSubmissionDate(LocalDateTime.now());
        complaintFeedback.setStatus("Submitted");

        return complaintFeedbackRepository.save(complaintFeedback);
    }

    /**
     * Submit anonymous complaint/feedback (without user)
     */
    public ComplaintFeedback submitAnonymousComplaintFeedback(FeedbackType feedbackType,
            String subject, String message) {
        ComplaintFeedback complaintFeedback = new ComplaintFeedback();
        complaintFeedback.setType(feedbackType);
        complaintFeedback.setSubject(subject);
        complaintFeedback.setMessage(message);
        complaintFeedback.setSubmissionDate(LocalDateTime.now());
        complaintFeedback.setStatus("Submitted");

        return complaintFeedbackRepository.save(complaintFeedback);
    }

    // === COMPLAINT/FEEDBACK RETRIEVAL ===

    /**
     * Get all complaints and feedback
     */
    public List<ComplaintFeedback> getAllComplaintsFeedback() {
        return complaintFeedbackRepository.findAll();
    }

    /**
     * Get complaint/feedback by ID
     */
    public Optional<ComplaintFeedback> getComplaintFeedbackById(Long complaintFeedbackId) {
        return complaintFeedbackRepository.findById(complaintFeedbackId);
    }

    /**
     * Get complaints/feedback by user ID
     */
    public List<ComplaintFeedback> getComplaintsFeedbackByUserId(Long userId) {
        return complaintFeedbackRepository.findByUser_UserIdOrderBySubmissionDateDesc(userId);
    }

    /**
     * Get complaints/feedback by type
     */
    public List<ComplaintFeedback> getComplaintsFeedbackByType(FeedbackType feedbackType) {
        return complaintFeedbackRepository.findByTypeOrderBySubmissionDateDesc(feedbackType);
    }

    /**
     * Search complaints/feedback by subject
     */
    public List<ComplaintFeedback> searchComplaintsFeedbackBySubject(String subject) {
        return complaintFeedbackRepository.findBySubjectContainingIgnoreCaseOrderBySubmissionDateDesc(subject);
    }

    // === COMPLAINT/FEEDBACK MANAGEMENT ===

    /**
     * Update complaint/feedback message
     */
    public ComplaintFeedback updateComplaintFeedbackMessage(Long complaintFeedbackId, String newMessage) {
        ComplaintFeedback complaintFeedback = complaintFeedbackRepository.findById(complaintFeedbackId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Complaint/Feedback not found: " + complaintFeedbackId));

        complaintFeedback.setMessage(newMessage);
        return complaintFeedbackRepository.save(complaintFeedback);
    }

    /**
     * Delete complaint/feedback
     */
    public void deleteComplaintFeedback(Long complaintFeedbackId) {
        if (!complaintFeedbackRepository.existsById(complaintFeedbackId)) {
            throw new IllegalArgumentException("Complaint/Feedback not found: " + complaintFeedbackId);
        }
        complaintFeedbackRepository.deleteById(complaintFeedbackId);
    }

    // === STATISTICS ===

    /**
     * Get complaint/feedback count by type
     */
    public long getComplaintFeedbackCountByType(FeedbackType feedbackType) {
        return complaintFeedbackRepository.countByType(feedbackType);
    }

    /**
     * Get total complaint/feedback count for user
     */
    public long getComplaintFeedbackCountByUserId(Long userId) {
        return complaintFeedbackRepository.countByUser_UserId(userId);
    }

    /**
     * Get total complaint/feedback count
     */
    public long getTotalComplaintFeedbackCount() {
        return complaintFeedbackRepository.count();
    }
}
