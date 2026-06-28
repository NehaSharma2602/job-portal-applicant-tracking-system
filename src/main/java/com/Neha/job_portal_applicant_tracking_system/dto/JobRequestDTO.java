package com.Neha.job_portal_applicant_tracking_system.dto;

import java.math.BigDecimal;

import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDTO {

	@NotBlank(message = "title is required")
	@Size(min = 2, max = 100, message = "title must be between 2 and 100 characters")
	private String title;
	
	@NotBlank(message = "description is required")
	@Size(max = 1000, message = "description cannot exceed 1000 characters")
	private String description;
	
	@NotBlank(message = "requiredSkills is required")
	private String requiredSkills;
	
	private int experienceRequired;
	
	@NotBlank(message = "salary is required")
	 @DecimalMin(value = "0.0", inclusive = false, message = "salary must be greater than 0")
	private BigDecimal salary;
	
	@NotBlank(message = "location is required")
	private String location;
	
	@NotBlank(message = "jobType is required")
	private JobType jobType;
	
	private JobStatus status =JobStatus.OPEN;
	
	@NotBlank(message = "companyId is required")
	private Long companyId;
}
