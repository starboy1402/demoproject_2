package com.digigov.backend.repository;

import com.digigov.backend.entity.Payment;
import com.digigov.backend.entity.PaymentStatus;
import com.digigov.backend.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find payment by application ID
    Optional<Payment> findByApplication_ApplicationId(Long applicationId);

    // Find payments by status
    List<Payment> findByStatusOrderByPaymentDateDesc(PaymentStatus status);

    // Find payments by payment method
    List<Payment> findByPaymentMethodOrderByPaymentDateDesc(PaymentMethod paymentMethod);

    // Find payments by amount range
    List<Payment> findByAmountBetweenOrderByPaymentDateDesc(BigDecimal minAmount, BigDecimal maxAmount);

    // Find payments by date range
    List<Payment> findByPaymentDateBetweenOrderByPaymentDateDesc(LocalDateTime startDate, LocalDateTime endDate);

    // Find payments by user ID through application
    List<Payment> findByApplication_User_UserIdOrderByPaymentDateDesc(Long userId);

    // Count payments by status
    long countByStatus(PaymentStatus status);

    // Find pending payments older than specific date
    List<Payment> findByStatusAndPaymentDateBeforeOrderByPaymentDateAsc(PaymentStatus status, LocalDateTime beforeDate);

    // Sum total amount by status
    Optional<BigDecimal> findTotalAmountByStatus(PaymentStatus status);
}
