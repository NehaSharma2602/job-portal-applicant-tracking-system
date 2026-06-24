package com.Neha.job_portal_applicant_tracking_system.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Neha.job_portal_applicant_tracking_system.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// Handles @NotBlank, @Email, etc. validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(),err.getDefaultMessage()));
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	// email already exists
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleEmailAlreadyExist(EmailAlreadyExistsException ex){
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.CONFLICT.value(),
				"Email already exist",
				ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
						
	}
	
	//inactive user
	@ExceptionHandler(UserInactiveException.class)
	public ResponseEntity<ErrorResponse> handleInactiveUser(UserInactiveException ex){
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.FORBIDDEN.value(),
				"User do not exists",
				ex.getMessage()
				);
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
	
	//Resource not found
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex){
			ErrorResponse error = new ErrorResponse(
					LocalDateTime.now(),
					HttpStatus.NOT_FOUND.value(),
					"Resource Not Found",
					ex.getMessage());
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		
		//generic exception means any other exception
	@ExceptionHandler(Exception.class)
		public ResponseEntity<ErrorResponse> handleGenericException(Exception ex){
			ErrorResponse error = new ErrorResponse(
					LocalDateTime.now(),
					HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Something went Wrong",
					ex.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
}
	
