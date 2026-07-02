package com.Neha.job_portal_applicant_tracking_system.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Neha.job_portal_applicant_tracking_system.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─────────────────────────────────────────────
    // VALIDATION ERRORS
    // ─────────────────────────────────────────────
    // This one stays different because it returns field-level errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationError(MethodArgumentNotValidException ex) {

        // Collect all field errors into a map
        // e.g. { "email": "invalid email format", "firstName": "firstName is required" }
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult()
          .getFieldErrors()
          .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(
            ApiResponse.error("Validation failed", fieldErrors),
            HttpStatus.BAD_REQUEST);
    }

    //==================== USER EXCEPTIONS=============================//
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExist(EmailAlreadyExistsException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.CONFLICT);
    }

    
    @ExceptionHandler(UserInactiveException.class)
    public ResponseEntity<ApiResponse<Void>> handleInactiveUser(UserInactiveException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.FORBIDDEN);
    }

    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.NOT_FOUND);
    }

    
    //======================== ROLE EXCEPTIONS======================================//
    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleConflict(RoleAlreadyExistsException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.CONFLICT);
    }

    
    //========================== COMPANY EXCEPTIONS=================================//
    @ExceptionHandler(CompanyAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleCompanyAlreadyExists(CompanyAlreadyExistsException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.CONFLICT);
    }

    
    @ExceptionHandler(CompanyInactiveException.class)
    public ResponseEntity<ApiResponse<Void>> handleCompanyInactive(CompanyInactiveException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.FORBIDDEN);
    }

    
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCompanyNotFound(CompanyNotFoundException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.NOT_FOUND);
    }

    
    //====================== JOB EXCEPTIONS=======================================//
    @ExceptionHandler(JobAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleJobAlreadyExists(JobAlreadyExistsException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.CONFLICT);
    }

    
    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleJobNotFound(JobNotFoundException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.NOT_FOUND);
    }

    
    @ExceptionHandler(JobNotOpenException.class)
    public ResponseEntity<ApiResponse<Void>> handleJobNotOpen(JobNotOpenException ex) {

        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    
    //=============== CANDIDATE EXCEPTIONS========================//
    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCandidateNotFound(CandidateNotFoundException ex) {

    	return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.NOT_FOUND);
    }

    
    @ExceptionHandler(CandidateAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleCandidateAlreadyExists(CandidateAlreadyExistsException ex) {

    	return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.CONFLICT);
    }

    
    @ExceptionHandler(CandidateInactiveException.class)
    public ResponseEntity<ApiResponse<Void>> handleCandidateInactive(CandidateInactiveException ex) {

    	return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.FORBIDDEN);
    }

    
    @ExceptionHandler(UserAlreadyLinkedToACandidateException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyLinked(UserAlreadyLinkedToACandidateException ex) {

    	return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.CONFLICT);
    }

    
    //========================== GENERIC FALLBACK===============================//
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {

        return new ResponseEntity<>(ApiResponse.error("Something went wrong: " + ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}