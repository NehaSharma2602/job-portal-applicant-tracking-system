package com.Neha.job_portal_applicant_tracking_system.exception;

public class JobNotOpenForApplicationException extends RuntimeException {
    public JobNotOpenForApplicationException(String message) {
        super(message);
    }
}