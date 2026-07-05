package com.Neha.job_portal_applicant_tracking_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.Neha.job_portal_applicant_tracking_system.entity.Gender;
import com.Neha.job_portal_applicant_tracking_system.entity.User;
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
	 private LocalDate dateOfBirth;          

	 @NotNull(message = "gender is required")
	 private Gender gender;

	 @NotBlank(message = "currentLocation is required")
	 private String currentLocation;

	 @Min(value = 0, message = "experienceYears cannot be negative")
	 private int experienceYears;                 

	 private String currentJobTitle;              

	 @DecimalMin(value = "0.0", inclusive = true, message = "currentSalary cannot be negative")
	 private BigDecimal currentSalary;            

	 @DecimalMin(value = "0.0", inclusive = false, message = "expectedSalary must be greater than 0")
	 private BigDecimal expectedSalary;           

	 @NotBlank(message = "skills is required")
	 private String skills;                       
	    
	 @NotNull(message = "userId is required")
	 private Long userId;
}
