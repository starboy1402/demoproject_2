package com.digigov.backend.controller;

import com.digigov.backend.entity.Service;
import com.digigov.backend.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/service")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    // === SERVICE RETRIEVAL ===

    /**
     * Get all services
     * GET /api/service/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<Service>> getAllServices() {
        try {
            List<Service> services = serviceRepository.findAll();
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get service by ID
     * GET /api/service/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceById(@PathVariable Long id) {
        try {
            Optional<Service> serviceOpt = serviceRepository.findById(id);
            if (serviceOpt.isPresent()) {
                return ResponseEntity.ok(serviceOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve service"));
        }
    }

    /**
     * Get service by name
     * GET /api/service/name/{name}
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getServiceByName(@PathVariable String name) {
        try {
            Optional<Service> serviceOpt = serviceRepository.findByServiceName(name);
            if (serviceOpt.isPresent()) {
                return ResponseEntity.ok(serviceOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve service"));
        }
    }

    /**
     * Get services by fee range
     * GET /api/service/fee-range?min={min}&max={max}
     */
    @GetMapping("/fee-range")
    public ResponseEntity<List<Service>> getServicesByFeeRange(
            @RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        try {
            List<Service> services = serviceRepository.findByFeeBetween(min, max);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get services with fee less than or equal to amount
     * GET /api/service/fee-max/{maxFee}
     */
    @GetMapping("/fee-max/{maxFee}")
    public ResponseEntity<List<Service>> getServicesByMaxFee(@PathVariable BigDecimal maxFee) {
        try {
            List<Service> services = serviceRepository.findByFeeLessThanEqual(maxFee);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // === SERVICE MANAGEMENT ===

    /**
     * Create a new service (Admin only)
     * POST /api/service/create
     */
    @PostMapping("/create")
    public ResponseEntity<?> createService(@RequestBody Map<String, Object> request) {
        try {
            String serviceName = request.get("serviceName").toString();
            String description = request.get("description").toString();
            BigDecimal fee = new BigDecimal(request.get("fee").toString());

            // Check if service name already exists
            if (serviceRepository.existsByServiceName(serviceName)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Service name already exists"));
            }

            Service service = new Service();
            service.setServiceName(serviceName);
            service.setDescription(description);
            service.setFee(fee);

            Service savedService = serviceRepository.save(service);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedService);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create service"));
        }
    }

    /**
     * Update service (Admin only)
     * PUT /api/service/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Optional<Service> serviceOpt = serviceRepository.findById(id);
            if (!serviceOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Service service = serviceOpt.get();

            if (request.containsKey("serviceName")) {
                String newServiceName = request.get("serviceName").toString();
                if (!service.getServiceName().equals(newServiceName) &&
                        serviceRepository.existsByServiceName(newServiceName)) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Service name already exists"));
                }
                service.setServiceName(newServiceName);
            }

            if (request.containsKey("description")) {
                service.setDescription(request.get("description").toString());
            }

            if (request.containsKey("fee")) {
                service.setFee(new BigDecimal(request.get("fee").toString()));
            }

            Service updatedService = serviceRepository.save(service);
            return ResponseEntity.ok(updatedService);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update service"));
        }
    }

    /**
     * Delete service (Admin only)
     * DELETE /api/service/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        try {
            if (!serviceRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            serviceRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Service deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete service"));
        }
    }

    // === STATISTICS ===

    /**
     * Get total service count
     * GET /api/service/count
     */
    @GetMapping("/count")
    public ResponseEntity<?> getServiceCount() {
        try {
            long count = serviceRepository.count();
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get service count"));
        }
    }
}
