package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when attempting to create a job with a title that already exists in the same company.
 * Handled by GlobalExceptionHandler — returns 409 CONFLICT.
 */
public class JobAlreadyExistsException extends RuntimeException {

	/**
     * @param message detailed message describing the conflict e.g. "Job with title 'Java Developer' already exists in this company"
     */
	public JobAlreadyExistsException(String message) {
		super(message);
	}

	
}
