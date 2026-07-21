package com.Neha.job_portal_applicant_tracking_system.entity;


/**
 * Enum representing the current status of a Job.
 * Stored as a String in the database using EnumType.STRING
 * for human-readable values.
 * Only OPEN jobs accept new applications from candidates.
 */
public enum JobStatus {
	
	/** Job is accepting applications from candidates */
	OPEN,
	
	/** Job is no longer accepting applications */
	CLOSED,
	
	/** Job posting is temporarily paused */
	ON_HOLD,
	
	/** Job position has been filled */
	FILLED
}
