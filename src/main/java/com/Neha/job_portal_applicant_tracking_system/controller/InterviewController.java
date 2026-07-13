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
import com.Neha.job_portal_applicant_tracking_system.dto.InterviewRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.InterviewResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewMode;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewResult;
import com.Neha.job_portal_applicant_tracking_system.service.InterviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InterviewResponseDTO>> scheduleInterview(@Valid @RequestBody InterviewRequestDTO interviewRequestDTO) {

        InterviewResponseDTO response = interviewService.scheduleInterview(interviewRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Interview scheduled successfully", response), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InterviewResponseDTO>> getInterviewById(@PathVariable Long id) {

        InterviewResponseDTO response = interviewService.getInterviewById(id);
        return new ResponseEntity<>(ApiResponse.success("Interview fetched successfully", response), HttpStatus.OK); 
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<ApiResponse<List<InterviewResponseDTO>>> getInterviewsByApplication(@PathVariable Long applicationId) {

        List<InterviewResponseDTO> response = interviewService.getInterviewsByApplication(applicationId);
        return new ResponseEntity<>(ApiResponse.success("Interviews fetched successfully", response), HttpStatus.OK); 
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<ApiResponse<List<InterviewResponseDTO>>> getInterviewsByCandidate(@PathVariable Long candidateId) {

        List<InterviewResponseDTO> response = interviewService.getInterviewsByCandidate(candidateId);
        return new ResponseEntity<>(ApiResponse.success("Interviews fetched successfully", response), HttpStatus.OK);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<InterviewResponseDTO>>> getInterviewsByJob(@PathVariable Long jobId) {

        List<InterviewResponseDTO> response = interviewService.getInterviewsByJob(jobId);
        return new ResponseEntity<>(ApiResponse.success("Interviews fetched successfully", response), HttpStatus.OK);
    }

    @GetMapping("/result")
    public ResponseEntity<ApiResponse<List<InterviewResponseDTO>>> getInterviewsByResult(@RequestParam InterviewResult result) {

        List<InterviewResponseDTO> response = interviewService.getInterviewsByResult(result);
        return new ResponseEntity<>(ApiResponse.success("Interviews fetched successfully", response), HttpStatus.OK); 
    }

    @GetMapping("/mode")
    public ResponseEntity<ApiResponse<List<InterviewResponseDTO>>> getInterviewsByMode(@RequestParam InterviewMode mode) {

        List<InterviewResponseDTO> response = interviewService.getInterviewsByMode(mode);
        return new ResponseEntity<>(ApiResponse.success("Interviews fetched successfully", response), HttpStatus.OK); 
    }

    @PatchMapping("/{id}/result")
    public ResponseEntity<ApiResponse<InterviewResponseDTO>> updateInterviewResult(@PathVariable Long id, @RequestParam InterviewResult result,
            @RequestParam(required = false) String feedback) {

        InterviewResponseDTO response = interviewService.updateInterviewResult(id, result, feedback);
        return new ResponseEntity<>(ApiResponse.success("Interview result updated successfully", response), HttpStatus.OK); 
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<ApiResponse<InterviewResponseDTO>> rescheduleInterview(@PathVariable Long id, 
    		@Valid @RequestBody InterviewRequestDTO interviewRequestDTO) {

        InterviewResponseDTO response =interviewService.rescheduleInterview(id, interviewRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Interview rescheduled successfully", response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInterview(@PathVariable Long id) {

        interviewService.deleteInterview(id);
        return new ResponseEntity<>(ApiResponse.success("Interview deleted successfully"), HttpStatus.NO_CONTENT); 
    }
}