package com.Neha.job_portal_applicant_tracking_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request DTO for creating a new Role.
 * Contains the role name sent by the client
 * in the request body.
 * Validation annotation ensures role name
 * is not blank before reaching the service layer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDTO {

	/**
     * Name of the role to create.
     * Expected values — ADMIN, RECRUITER, CANDIDATE.
     * Cannot be blank.
     */
    @NotBlank(message = "role is required")
	private String role;
}
