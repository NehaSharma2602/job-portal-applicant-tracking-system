package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.RoleRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.RoleResponseDTO;

public interface RoleService {

	//create
	RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);
	
	//get role by id
	RoleResponseDTO getRoleById(Long id);
	
	// get all role
	List<RoleResponseDTO> getAllRoles();
	
	//get role by name
	RoleResponseDTO getRoleByName(String role);
	
	void deleteRole(Long id);
}
