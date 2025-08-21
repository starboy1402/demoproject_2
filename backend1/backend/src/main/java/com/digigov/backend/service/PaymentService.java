package com.digigov.backend.service;

import com.digigov.backend.entity.*;
import com.digigov.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    // === PAYMENT CREATION ===

    /**
     * Create a new payment for an application (simplified)
     */
    public Payment createPayment(Application application, BigDecimal amount, String transactionId,
            String paymentMethod) {
        Payment payment = new Payment();
        payment.setApplication(application);
        payment.setAmount(amount);
        payment.setTransactionId(transactionId);

        // Convert string to PaymentMethod enum
        PaymentMethod method = PaymentMethod.valueOf(paymentMethod.toUpperCase());
        payment.setPaymentMethod(method);

        payment.setStatus(PaymentStatus.PENDING); // Set to PENDING on creation
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    /**
     * Get payments by application
     */
    public List<Payment> getPaymentsByApplication(Application application) {
        Optional<Payment> paymentOpt = paymentRepository
                .findByApplication_ApplicationId(application.getApplicationId());
        return paymentOpt.map(List::of).orElse(List.of());
    }

    /**
     * Create a new payment for an application
     */
    public Payment createPayment(Long applicationId, BigDecimal amount, PaymentMethod paymentMethod) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));

        Payment payment = new Payment();
        payment.setApplication(application);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // === PAYMENT RETRIEVAL ===

    /**
     * Get all payments
     */
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    /**
     * Get payment by ID
     */
    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    /**
     * Get payment by application ID
     */
    public Optional<Payment> getPaymentByApplicationId(Long applicationId) {
        return paymentRepository.findByApplication_ApplicationId(applicationId);
    }

    /**
     * Get payments by status
     */
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatusOrderByPaymentDateDesc(status);
    }

    /**
     * Get payments by user ID
     */
    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByApplication_User_UserIdOrderByPaymentDateDesc(userId);
    }

    // === PAYMENT MANAGEMENT ===

    /**
     * Complete payment (mark as COMPLETED)
     */
    public Payment completePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));

        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    /**
     * Fail payment (mark as FAILED)
     */
    public Payment failPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));

        payment.setStatus(PaymentStatus.FAILED);

        return paymentRepository.save(payment);
    }

    // === STATISTICS ===

    /**
     * Get payment count by status
     */
    public long getPaymentCountByStatus(PaymentStatus status) {
        return paymentRepository.countByStatus(status);
    }

    /**
     * Get total payments amount by status
     */
    public BigDecimal getTotalAmountByStatus(PaymentStatus status) {
        return paymentRepository.findTotalAmountByStatus(status).orElse(BigDecimal.ZERO);
    }
}
