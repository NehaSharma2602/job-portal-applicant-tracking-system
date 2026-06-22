package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.UserRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.UserResponseDTO;

public interface UserService {

	UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
	
	UserResponseDTO getUserById(Long id);
	
	UserResponseDTO getUserByEmail(String email);
	
	List<UserResponseDTO> getAllUser();
	
	UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
	
	void deactivateUser(Long id);
	
	void deleteUser(Long id);
	
	boolean emailExists(String email);

}
