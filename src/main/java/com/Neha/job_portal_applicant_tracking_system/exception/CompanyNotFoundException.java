package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when a requested Company is not found in the system.
 * Handled by GlobalExceptionHandler — returns 404 NOT FOUND.
 */
public class CompanyNotFoundException extends RuntimeException {

	/**
     * @param message detailed message describing what was not found e.g. "Company not found with id: 1"
     */
	public CompanyNotFoundException(String message) {
		super(message);
	}

	
}
