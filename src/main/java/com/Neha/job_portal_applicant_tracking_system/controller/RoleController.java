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

import com.Neha.job_portal_applicant_tracking_system.dto.RoleRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.RoleResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/role")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		super();
		this.roleService = roleService;
	}
	
	
	@PostMapping()
	public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO){
		RoleResponseDTO response = roleService.createRole(roleRequestDTO);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("{/id}")
	public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id){
		RoleResponseDTO response = roleService.getRoleById(id);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("name/{role}")
	public ResponseEntity<RoleResponseDTO> getRoleByName(@RequestBody String role){
		RoleResponseDTO response = roleService.getRoleByName(role);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<RoleResponseDTO>> getAllRoles(){
		List<RoleResponseDTO> response = roleService.getAllRoles();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable Long id) {

        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);              // 204
    }
	
	
}
