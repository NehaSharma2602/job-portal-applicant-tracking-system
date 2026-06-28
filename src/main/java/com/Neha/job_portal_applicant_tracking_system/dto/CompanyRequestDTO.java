package com.Neha.job_portal_applicant_tracking_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDTO {

	@NotBlank(message = "companyName is required")
	@Size(min = 2, max = 100, message = "companyName must be between 2 to 100 characters")
	private String companyName;
	
	@Size(max = 500, message = "description cannot exceed 500 words")
	private String description;
	
	@Pattern()
	private String website;
	
	private String industry;
	
	private String location;
	
}
