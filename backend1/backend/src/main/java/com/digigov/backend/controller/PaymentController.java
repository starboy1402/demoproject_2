package com.digigov.backend.controller;

import com.digigov.backend.entity.Payment;
import com.digigov.backend.entity.PaymentStatus;
import com.digigov.backend.entity.PaymentMethod;
import com.digigov.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // === PAYMENT CREATION ===

    /**
     * Create a new payment
     * POST /api/payment/create
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody Map<String, Object> request) {
        try {
            Long applicationId = Long.valueOf(request.get("applicationId").toString());
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            PaymentMethod paymentMethod = PaymentMethod.valueOf(request.get("paymentMethod").toString().toUpperCase());

            Payment payment = paymentService.createPayment(applicationId, amount, paymentMethod);
            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create payment"));
        }
    }

    // === PAYMENT RETRIEVAL ===

    /**
     * Get all payments
     * GET /api/payment/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get payment by ID
     * GET /api/payment/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long id) {
        try {
            Optional<Payment> paymentOpt = paymentService.getPaymentById(id);
            if (paymentOpt.isPresent()) {
                return ResponseEntity.ok(paymentOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve payment"));
        }
    }

    /**
     * Get payment by application ID
     * GET /api/payment/application/{applicationId}
     */
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<?> getPaymentByApplicationId(@PathVariable Long applicationId) {
        try {
            Optional<Payment> paymentOpt = paymentService.getPaymentByApplicationId(applicationId);
            if (paymentOpt.isPresent()) {
                return ResponseEntity.ok(paymentOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve payment"));
        }
    }

    /**
     * Get payments by status
     * GET /api/payment/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable String status) {
        try {
            PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
            List<Payment> payments = paymentService.getPaymentsByStatus(paymentStatus);
            return ResponseEntity.ok(payments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get payments by user ID
     * GET /api/payment/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@PathVariable Long userId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByUserId(userId);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // === PAYMENT MANAGEMENT ===

    /**
     * Complete payment
     * PUT /api/payment/{id}/complete
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completePayment(@PathVariable Long id) {
        try {
            Payment payment = paymentService.completePayment(id);
            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to complete payment"));
        }
    }

    /**
     * Fail payment
     * PUT /api/payment/{id}/fail
     */
    @PutMapping("/{id}/fail")
    public ResponseEntity<?> failPayment(@PathVariable Long id) {
        try {
            Payment payment = paymentService.failPayment(id);
            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fail payment"));
        }
    }

    // === STATISTICS ===

    /**
     * Get payment count by status
     * GET /api/payment/count/status/{status}
     */
    @GetMapping("/count/status/{status}")
    public ResponseEntity<?> getPaymentCountByStatus(@PathVariable String status) {
        try {
            PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
            long count = paymentService.getPaymentCountByStatus(paymentStatus);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid status"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get payment count"));
        }
    }

    /**
     * Get total amount by status
     * GET /api/payment/total/status/{status}
     */
    @GetMapping("/total/status/{status}")
    public ResponseEntity<?> getTotalAmountByStatus(@PathVariable String status) {
        try {
            PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
            BigDecimal total = paymentService.getTotalAmountByStatus(paymentStatus);
            return ResponseEntity.ok(Map.of("total", total));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid status"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get total amount"));
        }
    }
}
