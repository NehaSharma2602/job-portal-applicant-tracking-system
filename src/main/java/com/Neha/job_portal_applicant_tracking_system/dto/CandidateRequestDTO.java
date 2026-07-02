package com.Neha.job_portal_applicant_tracking_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.Neha.job_portal_applicant_tracking_system.entity.Gender;
import com.Neha.job_portal_applicant_tracking_system.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
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
public class CandidateRequestDTO {

	 @NotBlank(message = "firstName is required")
	 @Size(min = 2, max = 50, message = "firstName must be between 2 and 50 characters")
	 private String firstName;

	 @NotBlank(message = "lastName is required")
	 @Size(min = 2, max = 50, message = "lastName must be between 2 and 50 characters")
	 private String lastName;

	 @NotBlank(message = "email is required")
	 @Email(message = "invalid email format")
	 private String email;

	 @NotBlank(message = "phoneNumber is required")
	 private String phoneNumber;

	 @NotNull(message = "dateOfBirth is required")
	 private LocalDate dateOfBirth;               // LocalDate not LocalDateTime — only date needed

	 @NotNull(message = "gender is required")
	 private Gender gender;

	 @NotBlank(message = "currentLocation is required")
	 private String currentLocation;

	 @Min(value = 0, message = "experienceYears cannot be negative")
	 private int experienceYears;                 // e.g. 3

	 private String currentJobTitle;              // e.g. "Junior Developer" — optional

	 @DecimalMin(value = "0.0", inclusive = true, message = "currentSalary cannot be negative")
	 private BigDecimal currentSalary;            // optional — candidate may not want to share

	 @DecimalMin(value = "0.0", inclusive = false, message = "expectedSalary must be greater than 0")
	 private BigDecimal expectedSalary;           // optional

	 @NotBlank(message = "skills is required")
	 private String skills;                       // e.g. "Java, Spring Boot, MySQL"
	    
	 @NotNull(message = "user is required")
	 private User user;
}
