package com.Neha.job_portal_applicant_tracking_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Neha.job_portal_applicant_tracking_system.entity.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponseDTO {

	private Long id;
	private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String currentLocation;
    private int experienceYears;
    private String currentJobTitle;
    private BigDecimal currentSalary;
    private BigDecimal expectedSalary;
    private String skills;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // From User — only show name and email, not password
    private String userEmail;        // confirms which user this candidate is linked to
    private String userFirstName;
    private String userLastName;
}
