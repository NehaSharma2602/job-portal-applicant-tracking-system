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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Neha.job_portal_applicant_tracking_system.dto.ApiResponse;
import com.Neha.job_portal_applicant_tracking_system.dto.CandidateRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.CandidateResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Gender;
import com.Neha.job_portal_applicant_tracking_system.service.CandidateService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/candidates")
@AllArgsConstructor
public class CandidateController {

	private final CandidateService candidateService;

	@PostMapping
	public ResponseEntity<ApiResponse<CandidateResponseDTO>> createCandidate(@Valid @RequestBody CandidateRequestDTO candidateRequestDTO) {
		
		CandidateResponseDTO response = candidateService.createCandidate(candidateRequestDTO);
		return new ResponseEntity<>(ApiResponse.success("Candidate registered successfully", response), HttpStatus.CREATED);                                             
	 }
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CandidateResponseDTO>> getCandidateById(@PathVariable Long id) {

		CandidateResponseDTO response = candidateService.getCandidateById(id);
		return new ResponseEntity<>(ApiResponse.success("Candidate fetched successfully", response),HttpStatus.OK);                                                  
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponse<CandidateResponseDTO>> getCandidateByEmail(@PathVariable String email) {

		CandidateResponseDTO response = candidateService.getCandidateByEmail(email);
		return new ResponseEntity<>(ApiResponse.success("Candidate fetched successfully", response),HttpStatus.OK);                                                  
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<CandidateResponseDTO>> getCandidateByUserId(@PathVariable Long userId) {

		CandidateResponseDTO response = candidateService.getCandidateByUserId(userId);
		return new ResponseEntity<>(ApiResponse.success("Candidate fetched successfully", response),HttpStatus.OK);                                                  
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<CandidateResponseDTO>>> getAllCandidates() {

		List<CandidateResponseDTO> response = candidateService.getAllCandidates();
		return new ResponseEntity<>(ApiResponse.success("All candidates fetched successfully", response),HttpStatus.OK);                                                  
	}


	@GetMapping("/active")
	public ResponseEntity<ApiResponse<List<CandidateResponseDTO>>> getAllActiveCandidates() {

		List<CandidateResponseDTO> response = candidateService.getAllActiveCandidates();
		return new ResponseEntity<>(ApiResponse.success("Active candidates fetched successfully", response),HttpStatus.OK); 
	}

	@GetMapping("/inactive")
	public ResponseEntity<ApiResponse<List<CandidateResponseDTO>>> getAllInactiveCandidates() {
		List<CandidateResponseDTO> response = candidateService.getAllInactiveCandidates();
		return new ResponseEntity<>(ApiResponse.success("Inactive candidates fetched successfully", response),HttpStatus.OK);                                                  
	}

	@GetMapping("/location/{location}")
	public ResponseEntity<ApiResponse<List<CandidateResponseDTO>>> getCandidatesByLocation(@PathVariable String location) {
		List<CandidateResponseDTO> response =candidateService.getCandidatesByLocation(location);
		return new ResponseEntity<>(ApiResponse.success("Candidates fetched successfully", response),HttpStatus.OK);                                                  
	}


	@GetMapping("/gender")
	public ResponseEntity<ApiResponse<List<CandidateResponseDTO>>> getCandidatesByGender(@RequestParam Gender gender) {
		List<CandidateResponseDTO> response = candidateService.getCandidatesByGender(gender);
		return new ResponseEntity<>(ApiResponse.success("Candidates fetched successfully", response),HttpStatus.OK);                                                  
	}

	@GetMapping("/experience/max")
	public ResponseEntity<ApiResponse<List<CandidateResponseDTO>>> getCandidatesByMaxExperience(@RequestParam int years) {

		List<CandidateResponseDTO> response = candidateService.getCandidatesByMaxExperience(years);
		return new ResponseEntity<>(ApiResponse.success("Candidates fetched successfully", response),HttpStatus.OK);                                                  
	}

	@GetMapping("/experience/min")
	public ResponseEntity<ApiResponse<List<CandidateResponseDTO>>> getCandidatesByMinExperience(@RequestParam int years) {

		List<CandidateResponseDTO> response = candidateService.getCandidatesByMinExperience(years);
		return new ResponseEntity<>(ApiResponse.success("Candidates fetched successfully", response),HttpStatus.OK);                                                  
	    }

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CandidateResponseDTO>> updateCandidate(@PathVariable Long id,@Valid @RequestBody CandidateRequestDTO candidateRequestDTO) {

		CandidateResponseDTO response = candidateService.updateCandidate(id, candidateRequestDTO);
		return new ResponseEntity<>( ApiResponse.success("Candidate updated successfully", response), HttpStatus.OK);                                                  
	}

	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<ApiResponse<Void>> deactivateCandidate(@PathVariable Long id) {
		candidateService.deactivateCandidate(id);
		return new ResponseEntity<>(ApiResponse.success("Candidate deactivated successfully"),HttpStatus.OK);                                                  
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteCandidate(@PathVariable Long id) {

		candidateService.deleteCandidate(id);
		return new ResponseEntity<>(ApiResponse.success("Candidate deleted successfully"),HttpStatus.NO_CONTENT);                                          
	}
}

