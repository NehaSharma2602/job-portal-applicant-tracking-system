package com.Neha.job_portal_applicant_tracking_system.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Neha.job_portal_applicant_tracking_system.dto.ApiResponse;
import com.Neha.job_portal_applicant_tracking_system.dto.UserRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.UserResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.service.UserService;

import jakarta.validation.Valid;

/**
 * REST Controller for managing User operations.
 * Exposes endpoints for registering, retrieving, updating, deactivating and deleting users.
 * Base URL — /api/users
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	/** Service layer dependency for user business logic */
	private final UserService userService;

	 /**
     * Constructor injection of UserService.
     *
     * @param userService the service handling user business logic
     */
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	//full api : /api/users/register
	/**
     * Registers a new user in the system.
     * Validates request body fields before processing.
     *
     * @param userRequestDTO the user details from request body
     * @return ApiResponse containing the saved user details with HTTP status 201 CREATED
     */
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
		
		UserResponseDTO response = userService.registerUser(userRequestDTO);
		return new ResponseEntity<>(ApiResponse.success("Role created successfully", response), HttpStatus.CREATED);
		
	}
	
	/**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return ApiResponse containing the user details with HTTP status 200 OK
     */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id){
		
		UserResponseDTO response = userService.getUserById(id);
		return new ResponseEntity<>(ApiResponse.success("User fetched successfully", response), HttpStatus.OK);
	}
	
	/**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to retrieve
     * @return ApiResponse containing the user details with HTTP status 200 OK
     */
	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByEmail(@PathVariable String email){
		
		UserResponseDTO response = userService.getUserByEmail(email);
		return new ResponseEntity<>(ApiResponse.success("User fetched successfully", response), HttpStatus.OK);
	}
	
	/**
     * Retrieves all users registered in the system.
     *
     * @return ApiResponse containing list of all users with HTTP status 200 OK
     */
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserResponseDTO>>>  getAllUsers(){
		
		List<UserResponseDTO> response = userService.getAllUsers();
		return new ResponseEntity<>(ApiResponse.success("All users fetched successfully", response), HttpStatus.OK);
	}
	
	
	/**
     * Updates an existing user's details by their ID.
     * Validates request body fields before processing.
     *
     * @param id             the ID of the user to update
     * @param userRequestDTO the updated user details from request body
     * @return ApiResponse containing the updated user details with HTTP status 200 OK
     */
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> updateUsers(@PathVariable Long id, 
			@Valid @RequestBody UserRequestDTO userRequestDTO){
		
		UserResponseDTO response = userService.updateUser(id, userRequestDTO);
		return new ResponseEntity<>(ApiResponse.success("User updated successfully", response), HttpStatus.OK);
	}
	
	/**
     * Deactivates a user account by setting active = false.
     * This is a soft delete — user data is preserved in the database.
     *
     * @param id the ID of the user to deactivate
     * @return ApiResponse with success message with HTTP status 200 OK
     */
	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<ApiResponse<Void>> deactiveUser(@PathVariable Long id){
		
		userService.deactivateUser(id);
		return new ResponseEntity<>( ApiResponse.success("User deactivated successfully"), HttpStatus.OK);
	}
	
	/**
     * Permanently deletes a user from the system.
     * This is a hard delete — data cannot be recovered.
     *
     * @param id the ID of the user to delete
     * @return ApiResponse with success message with HTTP status 204 NO CONTENT
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id){
		
		userService.deleteUser(id);
		return new ResponseEntity<>(ApiResponse.success("User deleted successfully"), HttpStatus.NO_CONTENT);
	}
}
