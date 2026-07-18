package com.Neha.job_portal_applicant_tracking_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request DTO for creating or updating a Company.
 * Contains all fields sent by the client in the request body.
 * Validation annotations ensure data integrity before reaching the service layer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDTO {

	/**
     * Name of the company.
     * Must be unique and between 2 and 100 characters.
     * Cannot be blank.
     */
	@NotBlank(message = "companyName is required")
	@Size(min = 2, max = 100, message = "companyName must be between 2 to 100 characters")
	private String companyName;
	
	/**
     * Brief description of the company.
     * Optional field — can be null.
     * Cannot exceed 500 characters.
     */
	@Size(max = 500, message = "description cannot exceed 500 words")
	private String description;
	
	/**
     * Official website URL of the company.
     * Optional field — can be null.
     * Must be in valid URL format if provided.
     */
	@Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/.*)?$",
	        message = "Invalid website URL format")
	private String website;
	
	/**
     * Industry the company belongs to.
     * e.g. "IT", "Finance", "Healthcare".
     * Cannot be blank.
     */
	@NotBlank(message = "industry is required")
	private String industry;
	
	/**
     * Physical location of the company.
     * e.g. "Mumbai", "Delhi", "Bangalore".
     * Cannot be blank.
     */
	@NotBlank(message = "location is required")
	private String location;
	
}
