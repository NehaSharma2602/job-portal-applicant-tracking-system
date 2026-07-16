package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when attempting to register a user with an email that already exists in the system.
 * Handled by GlobalExceptionHandler — returns 409 CONFLICT.
 */
public class EmailAlreadyExistsException extends RuntimeException{
	
	/**
     * @param message detailed message describing the conflict e.g. "Email already registered: neha@gmail.com"
     */
	public EmailAlreadyExistsException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

}
