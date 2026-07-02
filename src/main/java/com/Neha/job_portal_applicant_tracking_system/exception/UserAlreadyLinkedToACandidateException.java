package com.Neha.job_portal_applicant_tracking_system.exception;

// Thrown when a User already has a Candidate profile
// One User can only be linked to One Candidate
public class UserAlreadyLinkedToACandidateException extends RuntimeException {
    public UserAlreadyLinkedToACandidateException(String message) {
        super(message);
    }
}