package com.Neha.job_portal_applicant_tracking_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Neha.job_portal_applicant_tracking_system.dto.ApiResponse;
import com.Neha.job_portal_applicant_tracking_system.dto.ApplicationRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.ApplicationResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.ApplicationStatus;
import com.Neha.job_portal_applicant_tracking_system.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApplicationResponseDTO>> applyForJob(@Valid @RequestBody ApplicationRequestDTO applicationRequestDTO) {

        ApplicationResponseDTO response =applicationService.applyForJob(applicationRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Application submitted successfully", response),HttpStatus.CREATED);    
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationResponseDTO>> getApplicationById(@PathVariable Long id) {

        ApplicationResponseDTO response =applicationService.getApplicationById(id);
        return new ResponseEntity<>(ApiResponse.success("Application fetched successfully", response),HttpStatus.OK);   
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<ApiResponse<List<ApplicationResponseDTO>>> getApplicationsByCandidate(@PathVariable Long candidateId) {

        List<ApplicationResponseDTO> response =applicationService.getApplicationsByCandidate(candidateId);
        return new ResponseEntity<>(ApiResponse.success("Applications fetched successfully", response),HttpStatus.OK);   
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<ApplicationResponseDTO>>> getApplicationsByJob( @PathVariable Long jobId) {

        List<ApplicationResponseDTO> response =applicationService.getApplicationsByJob(jobId);
        return new ResponseEntity<>(ApiResponse.success("Applications fetched successfully", response),HttpStatus.OK);   
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<List<ApplicationResponseDTO>>> getApplicationsByStatus(@RequestParam ApplicationStatus status) {

        List<ApplicationResponseDTO> response =applicationService.getApplicationsByStatus(status);
        return new ResponseEntity<>(ApiResponse.success("Applications fetched successfully", response),HttpStatus.OK); 
    }

 
    @GetMapping("/candidate/{candidateId}/status")
    public ResponseEntity<ApiResponse<List<ApplicationResponseDTO>>>getApplicationsByCandidateAndStatus( @PathVariable Long candidateId,
                @RequestParam ApplicationStatus status) {

        List<ApplicationResponseDTO> response = applicationService.getApplicationsByCandidateAndStatus(candidateId, status);
        return new ResponseEntity<>(ApiResponse.success("Applications fetched successfully", response),HttpStatus.OK);      
    }

    @GetMapping("/job/{jobId}/status")
    public ResponseEntity<ApiResponse<List<ApplicationResponseDTO>>>getApplicationsByJobAndStatus(@PathVariable Long jobId,
                @RequestParam ApplicationStatus status) {

        List<ApplicationResponseDTO> response = applicationService.getApplicationsByJobAndStatus(jobId, status);
        return new ResponseEntity<>(
            ApiResponse.success("Applications fetched successfully", response),HttpStatus.OK);   
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ApplicationResponseDTO>> updateApplicationStatus(@PathVariable Long id,
    		@RequestParam ApplicationStatus status) {

        ApplicationResponseDTO response = applicationService.updateApplicationStatus(id, status);
        return new ResponseEntity<>(ApiResponse.success("Application status updated successfully", response),HttpStatus.OK);  
    }
}
