package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response DTO returned after User operations.
 * Contains safe user details to return in API responses.
 * Sensitive fields like password are intentionally excluded to prevent data exposure.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	
	/** Unique identifier of the user */
	private Long id;
	
	/** First name of the user */
    private String firstName;
    
    /** Last name of the user */
    private String lastName;
    
    /** Email address of the user */
    private String email;
    
    /** Phone number of the user */
    private String phoneNumber;
    
    /**
     * Indicates whether the user account is active.
     * false means the user has been soft deleted.
     */
    private boolean active;
    
    /** Name of the role assigned to this user e.g. ADMIN, RECRUITER */
    private String roleName;
    
    /** Timestamp when the user account was created */
    private LocalDateTime createdAt;
    
    /** Timestamp when the user account was last updated */
    private LocalDateTime updatedAt;

}
