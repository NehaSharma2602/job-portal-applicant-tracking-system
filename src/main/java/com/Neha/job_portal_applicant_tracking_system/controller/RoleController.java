package com.Neha.job_portal_applicant_tracking_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Neha.job_portal_applicant_tracking_system.dto.ApiResponse;
import com.Neha.job_portal_applicant_tracking_system.dto.RoleRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.RoleResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.service.RoleService;

import jakarta.validation.Valid;

/**
 * REST Controller for managing Role operations.
 * Exposes endpoints for creating, retrieving and deleting roles in the system.
 * Base URL — /api/roles
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

	/** Service layer dependency for role business logic */
    private final RoleService roleService;

    /**
     * Constructor injection of RoleService.
     * Preferred over field injection for better testability.
     *
     * @param roleService the service handling role business logic
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Creates a new role in the system.
     * Validates request body before processing.
     *
     * @param roleRequestDTO the role details from request body
     * @return ApiResponse containing the saved role details
     *         with HTTP status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponseDTO>> createRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        RoleResponseDTO response = roleService.createRole(roleRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Role created successfully", response), HttpStatus.CREATED);
    }
    
    /**
     * Retrieves a role by its ID.
     *
     * @param id the ID of the role to retrieve
     * @return ApiResponse containing the role details with HTTP status 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> getRoleById(@PathVariable Long id) {

        RoleResponseDTO response = roleService.getRoleById(id);
        return new ResponseEntity<>(ApiResponse.success("Role fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves a role by its name.
     *
     * @param role the name of the role e.g. ADMIN, RECRUITER
     * @return ApiResponse containing the role details with HTTP status 200 OK
     */
    @GetMapping("/name/{role}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> getRoleByName(@PathVariable String role) {

        RoleResponseDTO response = roleService.getRoleByName(role);
        return new ResponseEntity<>(ApiResponse.success("Role fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all roles in the system.
     *
     * @return ApiResponse containing list of all roles with HTTP status 200 OK
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getAllRoles() {

        List<RoleResponseDTO> response = roleService.getAllRoles();
        return new ResponseEntity<>(ApiResponse.success("All roles fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Permanently deletes a role from the system.
     * This is a hard delete — data cannot be recovered.
     *
     * @param id the ID of the role to delete
     * @return ApiResponse with success message with HTTP status 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole( @PathVariable Long id) {

        roleService.deleteRole(id);
        return new ResponseEntity<>( ApiResponse.success("Role deleted successfully"), HttpStatus.NO_CONTENT);
    }
}