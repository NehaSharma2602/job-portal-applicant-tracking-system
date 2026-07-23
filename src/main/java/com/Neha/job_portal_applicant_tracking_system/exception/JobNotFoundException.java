package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when a requested Job is not found in the system.
 * Handled by GlobalExceptionHandler — returns 404 NOT FOUND.
 */
public class JobNotFoundException extends RuntimeException{

	/**
     * @param message detailed message describing what was not found e.g. "Job not found with id: 1"
     */
	public JobNotFoundException(String message) {
		super(message);
	}

	
}
