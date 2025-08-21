package com.digigov.backend.config;

import com.digigov.backend.entity.Admin;
import com.digigov.backend.entity.User;
import com.digigov.backend.entity.CitizenProfile;
import com.digigov.backend.entity.Service;
import com.digigov.backend.entity.Application;
import com.digigov.backend.entity.Payment;
import com.digigov.backend.entity.ApplicationStatus;
import com.digigov.backend.entity.PaymentStatus;
import com.digigov.backend.entity.Gender;
import com.digigov.backend.entity.Religion;
import com.digigov.backend.repository.AdminRepository;
import com.digigov.backend.repository.UserRepository;
import com.digigov.backend.repository.CitizenProfileRepository;
import com.digigov.backend.repository.ServiceRepository;
import com.digigov.backend.repository.ApplicationRepository;
import com.digigov.backend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CitizenProfileRepository citizenProfileRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize default admin account
        initializeAdminData();
        
        // Initialize demo user and profile
        initializeDemoData();
        
        // Initialize services
        initializeServices();
    }

    private void initializeAdminData() {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("admin123"); // In production, this should be hashed
            admin.setCreatedAt(LocalDateTime.now());
            adminRepository.save(admin);
            
            System.out.println("Default admin account created: admin/admin123");
        }
    }

    private void initializeDemoData() {
        if (userRepository.count() == 0) {
            // Create demo user
            User demoUser = new User();
            demoUser.setEmail("demo@teacher.com");
            demoUser.setPassword("secure123"); // In production, this should be hashed
            demoUser.setPhone("01987654321");
            demoUser.setCreatedAt(LocalDateTime.now());
            demoUser.setIsLoggedIn(true);
            User savedUser = userRepository.save(demoUser);

            // Create demo citizen profile
            CitizenProfile demoProfile = new CitizenProfile();
            demoProfile.setUser(savedUser);
            demoProfile.setName("Demo Teacher");
            demoProfile.setNidNumber("1234567890123");
            demoProfile.setDateOfBirth(LocalDate.of(1990, 1, 1));
            demoProfile.setFathersName("Demo Father");
            demoProfile.setMothersName("Demo Mother");
            demoProfile.setGender(Gender.MALE);
            demoProfile.setReligion(Religion.ISLAM);
            demoProfile.setProfession("Teacher");
            demoProfile.setCurrentAddress("Demo Address, Demo City");
            demoProfile.setPermanentAddress("Demo Address, Demo City");
            citizenProfileRepository.save(demoProfile);

            System.out.println("Demo user and profile created: demo@teacher.com/secure123");
        }
    }

    private void initializeServices() {
        if (serviceRepository.count() == 0) {
            // Create default services
            Service birthCert = new Service();
            birthCert.setServiceName("Birth Certificate Application");
            birthCert.setDescription("Apply for birth certificate");
            birthCert.setFee(500.0);
            birthCert.setIsActive(true);
            serviceRepository.save(birthCert);

            Service marriageCert = new Service();
            marriageCert.setServiceName("Marriage Certificate");
            marriageCert.setDescription("Apply for marriage certificate");
            marriageCert.setFee(1000.0);
            marriageCert.setIsActive(true);
            serviceRepository.save(marriageCert);

            Service deathCert = new Service();
            deathCert.setServiceName("Death Certificate");
            deathCert.setDescription("Apply for death certificate");
            deathCert.setFee(300.0);
            deathCert.setIsActive(true);
            serviceRepository.save(deathCert);

            System.out.println("Default services created");
        }
    }
}