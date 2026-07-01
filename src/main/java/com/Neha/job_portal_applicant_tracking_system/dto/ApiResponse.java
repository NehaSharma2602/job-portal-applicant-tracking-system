package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Generic API response wrapper used across all controllers.
 * Ensures every endpoint returns a consistent JSON structure;
 * 
 * @param<T> the type of data returned in the reponse
 */
@Getter
@Setter
public class ApiResponse<T> {

	/** It indicates whether the request was successful*/
	private boolean success;
	
	/**Readable message describing the result*/
	private String message;
	
	/**Actual response data — null for delete/deactivate operations*/
	private T data;
	
	 /** Time the response was generated */
	private LocalDateTime timestamp;
	
	//======== for success with data (put, patch, get, post)
	/**
	 * Creates a success response with data.
	 * 
	 * @param message  description of the success
	 * @param data     the actual response payload
	 * @param <T>      type of data
	  */
	public static <T> ApiResponse<T> success(String message, T data){
		ApiResponse<T> response = new ApiResponse<T>();
		response.success = true;
		response.message = message;
		response.data = data;
		response.timestamp = LocalDateTime.now();
		return response;
	}
	
	//========= for succes without data (delete, deactive)
	/**
	 * Creates a success reponse without data
	 * 
	 * @param message  description of the success
	 * @param data     type placeholder
	 * @return ApiResponse with success = true and null data*/
	
	public static <T> ApiResponse<T> success(String message){
		return success(message, null);
	}
	
	
	//error without data - handled by global handler
	
	/**
	 * creates an error response
	 * Typically used inside GlobalExceptionHandler
	 * 
	 * @param message description of the error
	 * @param <T>     type of placeholder
	 * @return ApiResponse with success = false and null data
	 */
	public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.message = message;
        response.data = null;
        response.timestamp = LocalDateTime.now();
        return response;
    }
	
	
	//error with data - handle bye global handler
	/**
	 * creates an error response
	 * Typically used inside GlobalExceptionHandler
	 * 
	 * @param message description of the error e.g. "Validation failed"
	 * @param data    the error details e.g. Map of field errors
	 * @param <T>     type of the data e.g. Map(String, String)
	 * @return ApiResponse with success = false and error data
	 */
	
	public static <T> ApiResponse<T> error(String message, T data) {  
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.message = message;
        response.data = data;                                          
        response.timestamp = LocalDateTime.now();
        return response;
    }
	
}
