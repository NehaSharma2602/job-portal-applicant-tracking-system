package com.Neha.job_portal_applicant_tracking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response DTO returned after Role operations.
 * Contains role details returned in API responses.
 * Kept simple as Role entity only has id and name.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDTO {

	/** Unique identifier of the role */
	private Long id;
	
	/** Name of the role e.g. ADMIN, RECRUITER, CANDIDATE */
	private String role;
}
