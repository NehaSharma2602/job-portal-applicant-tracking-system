package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when a candidate tries to apply for a job that is not OPEN for applications.
 * Handled by GlobalExceptionHandler — returns 400 BAD REQUEST.
 */
public class JobNotOpenException extends RuntimeException {

	/**
     * @param message detailed message describing the issue e.g. "Job is not open for applications. Current status: CLOSED"
     */
	public JobNotOpenException(String message) {
		super(message);
	}

	
}
