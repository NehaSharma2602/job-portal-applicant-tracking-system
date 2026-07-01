package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.RoleRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.RoleResponseDTO;

/**
 * Service interface for managing Role operations.
 * Handles business logic for creating and retrieving roles.
 */
public interface RoleService {

    /**
     * Creates a new role in the system.
     *
     * @param roleRequestDTO the role details from the request body
     * @return RoleResponseDTO the saved role details
     * @throws RoleAlreadyExistsException if a role with the same name already exists
     */
    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);

    /**
     * Retrieves a role by its ID.
     *
     * @param id the ID of the role
     * @return RoleResponseDTO the role details
     * @throws ResourceNotFoundException if no role is found with the given ID
     */
    RoleResponseDTO getRoleById(Long id);

    /**
     * Retrieves a role by its name.
     *
     * @param role the name of the role e.g. "ADMIN", "APPLICANT"
     * @return RoleResponseDTO the role details
     * @throws ResourceNotFoundException if no role is found with the given name
     */
    RoleResponseDTO getRoleByName(String role);

    /**
     * Retrieves all roles in the system.
     *
     * @return List of RoleResponseDTO
     * @throws ResourceNotFoundException if no roles exist
     */
    List<RoleResponseDTO> getAllRoles();

    /**
     * Deletes a role permanently from the system.
     *
     * @param id the ID of the role to delete
     * @throws ResourceNotFoundException if no role is found with the given ID
     */
    void deleteRole(Long id);
}
