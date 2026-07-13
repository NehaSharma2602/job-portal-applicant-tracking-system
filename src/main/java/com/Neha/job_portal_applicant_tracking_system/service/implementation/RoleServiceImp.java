package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.RoleRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.RoleResponseDTO;
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
        this.roleRepo = roleRepo;
    }
    
    //======================================= private mapper - role entity -> RoleresponseDTO =======================================//
    private RoleResponseDTO mapToResponseDTO(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(role.getId());
        dto.setRole(role.getRole());
        return dto;
    }

    //===================================== create role ==================================================//
    @Override
    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO dto) {

        if (roleRepo.existsByRole(dto.getRole())) {
            throw new RoleAlreadyExistsException("Role already exists: " + dto.getRole());
        }

        Role role = new Role();
        role.setRole(dto.getRole());

        return mapToResponseDTO(roleRepo.save(role));
    }

    //========================================== get role by id ===============================================//
    @Override
    public RoleResponseDTO getRoleById(Long id) {

        Role role = roleRepo.findById(id).orElseThrow(
        		() -> new ResourceNotFoundException("Role not found with id: " + id));

        return mapToResponseDTO(role);
    }

    //========================================== get role by name ===============================================//
    @Override
    public RoleResponseDTO getRoleByName(String roleName) {

        Role role = roleRepo.findByRole(roleName).orElseThrow(
        		() -> new ResourceNotFoundException("Role not found with name: " + roleName));

        return mapToResponseDTO(role);
    }

    //============================================= get all roles ================================================//
    @Override
    public List<RoleResponseDTO> getAllRoles() {

        List<Role> roles = roleRepo.findAll();

        if (roles.isEmpty()) {
            throw new ResourceNotFoundException("No roles found in the system");
        }

        List<RoleResponseDTO> response = new ArrayList<>();
        for(Role role : roles) {
        	RoleResponseDTO dto = mapToResponseDTO(role);
        	response.add(dto);
        }
        return response;
    }

    //============================================ delete role ===========================================//
    @Override
    @Transactional
    public void deleteRole(Long id) {

        Role role = roleRepo.findById(id).orElseThrow(
        		() -> new ResourceNotFoundException("Role not found with id: " + id));

        roleRepo.delete(role);
    }

    
}