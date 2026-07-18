package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when attempting to perform an operation on a deactivated company.
 * Handled by GlobalExceptionHandler — returns 403 FORBIDDEN.
 */
public class CompanyInactiveException extends RuntimeException{

	/**
     * @param message detailed message describing the issue e.g. "Cannot update inactive company with id: 1"
     */
	public CompanyInactiveException(String message) {
		super(message);
	}

	
}
