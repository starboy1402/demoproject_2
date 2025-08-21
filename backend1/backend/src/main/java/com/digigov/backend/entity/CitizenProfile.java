package com.digigov.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "citizen_profile")
public class CitizenProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    // Name validation: Required, max 50 characters
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters", min = 1)
    @Column(name = "name", nullable = false)
    private String name;

    // NID validation: Required, exactly 13 digits, unique
    @NotBlank(message = "NID number is required")
    @Pattern(regexp = "^\\d{13}$", message = "NID number must be exactly 13 digits")
    @Column(name = "nid_number", nullable = false, unique = true)
    private String nidNumber;

    // Date of Birth validation: Required field
    @NotNull(message = "Date of birth is required")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    // Father's name validation: Required, max 50 characters
    @NotBlank(message = "Father's name is required")
    @Size(max = 50, message = "Father's name cannot exceed 50 characters", min = 1)
    @Column(name = "fathers_name", nullable = false)
    private String fathersName;

    // Mother's name validation: Required, max 50 characters
    @NotBlank(message = "Mother's name is required")
    @Size(max = 50, message = "Mother's name cannot exceed 50 characters", min = 1)
    @Column(name = "mothers_name", nullable = false)
    private String mothersName;

    // Replace these fields in CitizenProfile.java
    // Religion validation: Required, enum values only
    @NotNull(message = "Religion is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "religion", nullable = false)
    private Religion religion;

    // Gender validation: Required, enum values only
    @NotNull(message = "Gender is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    // Profession validation: Required, just text
    @NotBlank(message = "Profession is required")
    @Column(name = "profession", nullable = false)
    private String profession;

    // Current address validation: Required, max 255 characters
    @NotBlank(message = "Current address is required")
    @Size(max = 255, message = "Current address cannot exceed 255 characters", min = 1)
    @Column(name = "current_address", nullable = false)
    private String currentAddress;

    // Permanent address validation: Required, max 255 characters
    @NotBlank(message = "Permanent address is required")
    @Size(max = 255, message = "Permanent address cannot exceed 255 characters", min = 1)
    @Column(name = "permanent_address", nullable = false)
    private String permanentAddress;

    // UPDATED: User relationship with explicit foreign key constraint
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CITIZEN_PROFILE_USER", foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User user;

    // CitizenProfile → Applications: CASCADE DELETE (when profile deleted, delete
    // applications)
    @OneToMany(mappedBy = "citizenProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;

    // Default constructor
    public CitizenProfile() {
    }

    // Constructor with User object
    public CitizenProfile(String name, String nidNumber, User user) {
        this.name = name;
        this.nidNumber = nidNumber;
        this.user = user;
    }

    // All getters and setters remain the same...
    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNidNumber() {
        return nidNumber;
    }

    public void setNidNumber(String nidNumber) {
        this.nidNumber = nidNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}