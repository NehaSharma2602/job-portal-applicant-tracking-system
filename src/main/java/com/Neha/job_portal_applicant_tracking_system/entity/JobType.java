package com.Neha.job_portal_applicant_tracking_system.entity;

import jakarta.persistence.Entity;

/**
 * Enum representing the type of a Job.
 * Stored as a String in the database using EnumType.STRING
 * for human-readable values.
 */
public enum JobType {

	/** Full time permanent position */
	FULL_TIME,
	
	/** Part time position with reduced hours */
    PART_TIME,
    
    /** Fixed term contract position */
    CONTRACT,
    
    /** Internship position for students or freshers */
    INTERNSHIP,
    
    /** Freelance or project based position */
    FREELANCE,
    
    /** Fully remote work from home position */
    REMOTE
}
