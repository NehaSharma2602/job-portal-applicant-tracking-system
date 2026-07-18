package com.Neha.job_portal_applicant_tracking_system.dto;


import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Response DTO returned after Company operations.
 * Contains company details along with its list of jobs.
 * Jobs are represented as {@link JobResponseDTO} to avoid
 * infinite nesting — JobResponseDTO only stores companyName
 * as a String instead of the full Company object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDTO {

	/** Unique identifier of the company */
    private Long id;
    
    /** Name of the company */
    private String companyName;
    
    /** Brief description of the company */
    private String description;
    
    /** Official website URL of the company */
    private String website;
    
    /** Industry the company belongs to */
    private String industry;
    
    /** Physical location of the company */
    private String location;
    
    /**
     * Indicates whether the company is active.
     * false means the company has been soft deleted.
     */
    private boolean active;
    
    /** Timestamp when the company was created */
    private LocalDateTime createdAt;
    
    /** Timestamp when the company was last updated */
    private LocalDateTime updatedAt;
    
    /**
     * List of jobs posted by this company.
     * Uses JobResponseDTO instead of Job entity
     * to prevent circular reference in JSON response.
     */
    private List<JobResponseDTO> jobs;     // Shows all jobs under this company

}
