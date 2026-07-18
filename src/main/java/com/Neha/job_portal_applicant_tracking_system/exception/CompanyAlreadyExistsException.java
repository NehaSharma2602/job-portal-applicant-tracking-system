package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when attempting to create a company with a name that already exists in the system.
 * Handled by GlobalExceptionHandler — returns 409 CONFLICT.
 */
public class CompanyAlreadyExistsException extends RuntimeException {

	/**
     * @param message detailed message describing the conflict e.g. "Company already exists with name: Google"
     */
	public CompanyAlreadyExistsException(String message) {
		super(message);
	}

	
}
