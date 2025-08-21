package com.digigov.backend.repository;

import com.digigov.backend.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    // Find service by name
    Optional<Service> findByServiceName(String serviceName);

    // Check if service name exists
    boolean existsByServiceName(String serviceName);

    // Find services by fee range
    List<Service> findByFeeBetween(BigDecimal minFee, BigDecimal maxFee);

    // Find services by fee less than or equal to
    List<Service> findByFeeLessThanEqual(BigDecimal maxFee);
}
