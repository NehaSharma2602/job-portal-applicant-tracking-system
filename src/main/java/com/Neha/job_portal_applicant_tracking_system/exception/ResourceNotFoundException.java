package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when a requested User is not found in the system.
 * Handled by GlobalExceptionHandler — returns 404 NOT FOUND.
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
     * @param message detailed message describing what was not found e.g. "User not found with id: 1"
     */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
