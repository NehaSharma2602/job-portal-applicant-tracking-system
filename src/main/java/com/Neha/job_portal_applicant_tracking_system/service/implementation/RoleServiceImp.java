package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.RoleRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.RoleResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.UserResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Role;
import com.Neha.job_portal_applicant_tracking_system.exception.ResourceNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.RoleAlreadyExistsException;
import com.Neha.job_portal_applicant_tracking_system.repository.RoleRepository;
import com.Neha.job_portal_applicant_tracking_system.service.RoleService;

import jakarta.transaction.Transactional;

@Service
public class RoleServiceImp implements RoleService {

	private final RoleRepository roleRepo;

	public RoleServiceImp(RoleRepository roleRepo) {
		super();
		this.roleRepo = roleRepo;
	}
	
	public RoleResponseDTO mapToResponseDTO(Role role) {
		RoleResponseDTO dto = new RoleResponseDTO();
		dto.setId(role.getId());
		dto.setRole(role.getRole());
		
		return dto;
	}
	
	@Override
    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO dto) {

        if (roleRepo.existsByRole(dto.getRole())) {
            throw new RoleAlreadyExistsException(
                "Role already exists: " + dto.getRole());
        }

        Role role = new Role();
        role.setRole(dto.getRole());

        return mapToResponseDTO(roleRepo.save(role));
    }
	
	@Override
	public RoleResponseDTO getRoleById(Long id) {
		Role role = roleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + id));
		return mapToResponseDTO(role);
	}
	
	//find role by role name
	@Override
	public RoleResponseDTO getRoleByName(String role) {
		Role role1 = roleRepo.findByRole(role).orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + role));
		
		return mapToResponseDTO(role1);
	}
	
	@Override
	public List<RoleResponseDTO> getAllRoles(){
		List<Role> role = roleRepo.findAll();
		List<RoleResponseDTO> result = new ArrayList<>();
		if(role.isEmpty()) {
			throw new ResourceNotFoundException("No role found in the system");
		}
		
		for(Role r1: role) {
			result.add(mapToResponseDTO(r1));
		}
		
		return result;
	}
	
	@Override
    @Transactional
    public void deleteRole(Long id) {

        Role role = roleRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Role not found with id: " + id));

        roleRepo.delete(role);
    }
}
