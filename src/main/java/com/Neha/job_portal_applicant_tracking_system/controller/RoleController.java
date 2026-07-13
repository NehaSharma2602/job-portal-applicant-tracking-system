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

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponseDTO>> createRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        RoleResponseDTO response = roleService.createRole(roleRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Role created successfully", response), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> getRoleById(@PathVariable Long id) {

        RoleResponseDTO response = roleService.getRoleById(id);
        return new ResponseEntity<>(ApiResponse.success("Role fetched successfully", response), HttpStatus.OK);
    }

    @GetMapping("/name/{role}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> getRoleByName(@PathVariable String role) {

        RoleResponseDTO response = roleService.getRoleByName(role);
        return new ResponseEntity<>(ApiResponse.success("Role fetched successfully", response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getAllRoles() {

        List<RoleResponseDTO> response = roleService.getAllRoles();
        return new ResponseEntity<>(ApiResponse.success("All roles fetched successfully", response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole( @PathVariable Long id) {

        roleService.deleteRole(id);
        return new ResponseEntity<>( ApiResponse.success("Role deleted successfully"), HttpStatus.NO_CONTENT);
    }
}