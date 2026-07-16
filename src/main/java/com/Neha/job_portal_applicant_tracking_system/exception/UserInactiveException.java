package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when attempting to perform an operation
 * on a deactivated user account.
 * Handled by GlobalExceptionHandler — returns 403 FORBIDDEN.
 */
public class UserInactiveException extends RuntimeException {
	
	/**
     * @param message detailed message describing the issue
     *                e.g. "User account is deactivated for id: 1"
     */
	public UserInactiveException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

}
