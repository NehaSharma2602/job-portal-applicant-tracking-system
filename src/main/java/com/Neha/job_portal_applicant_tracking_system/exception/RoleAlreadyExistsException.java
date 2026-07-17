package com.Neha.job_portal_applicant_tracking_system.exception;

/**
 * Thrown when attempting to create a role with a name that already exists in the system.
 * Handled by GlobalExceptionHandler — returns 409 CONFLICT.
 */
public class RoleAlreadyExistsException extends RuntimeException{

	/**
     * @param message detailed message describing the conflict e.g. "Role already exists: ADMIN"
     */
	public RoleAlreadyExistsException(String message) {
		super(message);
	}
	
	

}
