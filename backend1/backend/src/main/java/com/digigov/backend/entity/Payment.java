package com.digigov.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;
    
    // Application relationship: Required with explicit foreign key constraint
    @NotNull(message = "Application is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false,
        foreignKey = @ForeignKey(
            name = "FK_PAYMENT_APPLICATION",
            foreignKeyDefinition = "FOREIGN KEY (application_id) REFERENCES application(application_id) ON DELETE CASCADE ON UPDATE CASCADE"
        )
    )
    private Application application;
    
    // Amount validation: Required, must be > 0
    @NotNull(message = "Payment amount is required")
    @DecimalMin(value = "0.01", message = "Payment amount must be greater than 0")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    // Payment method validation: Required, enum values only
    @NotNull(message = "Payment method is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    
    // Transaction ID validation: Required, unique, alphanumeric, max 100 characters
    @NotBlank(message = "Transaction ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Transaction ID must contain only letters and numbers")
    @Size(max = 100, message = "Transaction ID cannot exceed 100 characters", min = 1)
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;
    
    // Payment status validation: Required, defaults to PENDING
    @NotNull(message = "Payment status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;
    
    // Payment date: Auto-generated, required
    @NotNull(message = "Payment date is required")
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;
    
    // Default constructor
    public Payment() {
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }
    
    // Constructor with required parameters
    public Payment(Application application, BigDecimal amount, PaymentMethod paymentMethod, String transactionId) {
        this.application = application;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }
    
    // Getters and setters
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    
    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}