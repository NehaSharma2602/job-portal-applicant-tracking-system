package com.Neha.job_portal_applicant_tracking_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response DTO returned after Job operations.
 * Contains job details along with the company name.
 * Only companyName is included as a String — not the full Company object — to prevent circular JSON nesting.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDTO {

	 /** Unique identifier of the job */
	private Long id;
	
	 /** Title of the job position */
    private String title;
    
    /** Detailed description of the job */
    private String description;
    
    /**
     * Skills required for the job.
     * Stored as comma-separated string. e.g. "Java, Spring Boot, MySQL"
     */
    private String requiredSkills;
    
    /** Years of experience required for the job */
    private int experienceRequired;
    
    /** Salary offered for the position */
    private BigDecimal salary;
    
    /** Location where the job is based */
    private String location;
    
    /** Type of the job e.g. FULL_TIME, REMOTE */
    private JobType jobType;
    
    /**
     * Current status of the job.
     * e.g. OPEN, CLOSED, ON_HOLD, FILLED
     */
    private JobStatus status;
    
    /** Timestamp when the job was created */
    private LocalDateTime createdAt;
    
    /** Timestamp when the job was last updated */
    private LocalDateTime updatedAt;
    
    
    //Company → jobs → JobResponseDTO (has companyName string, not full Company object)
    /**
     * Name of the company that posted this job.
     * Only name is stored — not the full Company object — to prevent infinite nesting in JSON response.
     */
    private String companyName;     
}
