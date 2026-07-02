package com.Neha.job_portal_applicant_tracking_system.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidate")
public class Candidate {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "candidate_id")
	 private Long id;

	 @NotBlank(message = "firstName is required")
	 @Size(min = 2, max = 50, message = "firstName must be between 2 and 50 characters")
	 @Column(nullable = false, name = "first_name")
	 private String firstName;

	 @NotBlank(message = "lastName is required")
	 @Size(min = 2, max = 50, message = "lastName must be between 2 and 50 characters")
	 @Column(nullable = false, name = "last_name")
	 private String lastName;

	 @NotBlank(message = "email is required")
	 @Column(nullable = false, unique = true)
	 private String email;

	 @NotBlank(message = "phoneNumber is required")
	 @Column(nullable = false, name = "phone_number")
	 private String phoneNumber;

	 @NotNull(message = "dateOfBirth is required")
	 @Column(name = "date_of_birth")
	 private LocalDate dateOfBirth;               // LocalDate not LocalDateTime — only date needed

	 @NotNull(message = "gender is required")
	 @Enumerated(EnumType.STRING)                 // Stores "MALE" not 0/1 in DB
	 @Column(nullable = false)
	 private Gender gender;

	 @NotBlank(message = "currentLocation is required")
	 @Column(nullable = false, name = "current_location")
	 private String currentLocation;

	 @Min(value = 0, message = "experienceYears cannot be negative")
	 @Column(nullable = false, name = "experience_years")
	 private int experienceYears;                 // e.g. 3

	 @Column(name = "current_job_title")
	 private String currentJobTitle;              // e.g. "Junior Developer" — optional

	 @DecimalMin(value = "0.0", inclusive = true, message = "currentSalary cannot be negative")
	 @Column(name = "current_salary", precision = 10, scale = 2)
	 private BigDecimal currentSalary;            // optional — candidate may not want to share

	 @DecimalMin(value = "0.0", inclusive = false, message = "expectedSalary must be greater than 0")
	 @Column(name = "expected_salary", precision = 10, scale = 2)
	 private BigDecimal expectedSalary;           // optional

	 @NotBlank(message = "skills is required")
	 @Column(nullable = false, length = 500)
	 private String skills;                       // e.g. "Java, Spring Boot, MySQL"

	 @Column(nullable = false, name = "is_active")
	 private boolean active = true;

	 @CreationTimestamp
	 @Column(updatable = false, name = "created_at")
	 private LocalDateTime createdAt;

	 @UpdateTimestamp
	 @Column(name = "updated_at")
	 private LocalDateTime updatedAt;
	    
	 @NotNull(message = "user is required")
	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "user_id", nullable = false, unique = true)
	 // unique = true → one user can only be one candidate
	 private User user;
	
}
