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

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	//full api : /api/users/register
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
		
		UserResponseDTO response = userService.registerUser(userRequestDTO);
		return new ResponseEntity<>(ApiResponse.success("Role created successfully", response), HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id){
		
		UserResponseDTO response = userService.getUserById(id);
		return new ResponseEntity<>(ApiResponse.success("User fetched successfully", response), HttpStatus.OK);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByEmail(@PathVariable String email){
		
		UserResponseDTO response = userService.getUserByEmail(email);
		return new ResponseEntity<>(ApiResponse.success("User fetched successfully", response), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserResponseDTO>>>  getAllUsers(){
		
		List<UserResponseDTO> response = userService.getAllUsers();
		return new ResponseEntity<>(ApiResponse.success("All users fetched successfully", response), HttpStatus.OK);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> updateUsers(@PathVariable Long id, 
			@Valid @RequestBody UserRequestDTO userRequestDTO){
		
		UserResponseDTO response = userService.updateUser(id, userRequestDTO);
		return new ResponseEntity<>(ApiResponse.success("User updated successfully", response), HttpStatus.OK);
	}
	
	
	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<ApiResponse<Void>> deactiveUser(@PathVariable Long id){
		
		userService.deactivateUser(id);
		return new ResponseEntity<>( ApiResponse.success("User deactivated successfully"), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id){
		
		userService.deleteUser(id);
		return new ResponseEntity<>(ApiResponse.success("User deleted successfully"), HttpStatus.NO_CONTENT);
	}
}
