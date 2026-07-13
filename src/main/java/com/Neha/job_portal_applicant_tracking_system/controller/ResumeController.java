package com.Neha.job_portal_applicant_tracking_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Neha.job_portal_applicant_tracking_system.dto.ApiResponse;
import com.Neha.job_portal_applicant_tracking_system.dto.ResumeRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.ResumeResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.service.ResumeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/resumes")
@AllArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ApiResponse<ResumeResponseDTO>> uploadResume(@Valid @RequestBody ResumeRequestDTO resumeRequestDTO) {

        ResumeResponseDTO response = resumeService.uploadResume(resumeRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Resume uploaded successfully", response), HttpStatus.CREATED);      
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResumeResponseDTO>> getResumeById(@PathVariable Long id) {

        ResumeResponseDTO response = resumeService.getResumeById(id);
        return new ResponseEntity<>(ApiResponse.success("Resume fetched successfully", response), HttpStatus.OK);
       
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<ApiResponse<List<ResumeResponseDTO>>> getResumesByCandidateId(@PathVariable Long candidateId) {

        List<ResumeResponseDTO> response = resumeService.getResumesByCandidateId(candidateId);
        return new ResponseEntity<>(ApiResponse.success("Resumes fetched successfully", response), HttpStatus.OK);                                             
    }

    @GetMapping("/candidate/{candidateId}/default")
    public ResponseEntity<ApiResponse<ResumeResponseDTO>> getDefaultResume(@PathVariable Long candidateId) {

        ResumeResponseDTO response = resumeService.getDefaultResume(candidateId);
        return new ResponseEntity<>(ApiResponse.success("Default resume fetched successfully", response), HttpStatus.OK);    
        
    }

    @PatchMapping("/{resumeId}/default/{candidateId}")
    public ResponseEntity<ApiResponse<ResumeResponseDTO>> setDefaultResume(@PathVariable Long resumeId, @PathVariable Long candidateId) {

        ResumeResponseDTO response = resumeService.setDefaultResume(resumeId, candidateId);
        return new ResponseEntity<>(ApiResponse.success("Default resume updated successfully", response), HttpStatus.OK);                                             
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteResume(@PathVariable Long id) {

        resumeService.deleteResume(id);
        return new ResponseEntity<>(ApiResponse.success("Resume deleted successfully"), HttpStatus.NO_CONTENT);       
    }
}