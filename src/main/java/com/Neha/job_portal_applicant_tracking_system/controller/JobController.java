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
import com.Neha.job_portal_applicant_tracking_system.dto.JobRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.JobResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;
import com.Neha.job_portal_applicant_tracking_system.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobResponseDTO>> createJob(
            @Valid @RequestBody JobRequestDTO jobRequestDTO) {

        JobResponseDTO response = jobService.createJob(jobRequestDTO);
        return new ResponseEntity<>(
            ApiResponse.success("Job created successfully", response),
            HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponseDTO>> getJobById(
            @PathVariable Long id) {

        JobResponseDTO response = jobService.getJobById(id);
        return new ResponseEntity<>(
            ApiResponse.success("Job fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getAllJobs() {

        List<JobResponseDTO> response = jobService.getAllJobs();
        return new ResponseEntity<>(
            ApiResponse.success("All jobs fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByCompany(
            @PathVariable Long companyId) {

        List<JobResponseDTO> response = jobService.getJobsByCompany(companyId);
        return new ResponseEntity<>(
            ApiResponse.success("Jobs fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByStatus(
            @PathVariable JobStatus status) {

        List<JobResponseDTO> response = jobService.getJobsByStatus(status);
        return new ResponseEntity<>(
            ApiResponse.success("Jobs fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/type/{jobType}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByType(
            @PathVariable JobType jobType) {

        List<JobResponseDTO> response = jobService.getJobsByType(jobType);
        return new ResponseEntity<>(
            ApiResponse.success("Jobs fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByLocation(
            @PathVariable String location) {

        List<JobResponseDTO> response = jobService.getJobsByLocation(location);
        return new ResponseEntity<>(
            ApiResponse.success("Jobs fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}/status/{status}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByCompanyAndStatus(
            @PathVariable Long companyId,
            @PathVariable JobStatus status) {

        List<JobResponseDTO> response =
                jobService.getJobsByCompanyAndStatus(companyId, status);
        return new ResponseEntity<>(
            ApiResponse.success("Jobs fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/experience")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByMaxExperience(
            @RequestParam int max) {

        List<JobResponseDTO> response = jobService.getJobsByMaxExperience(max);
        return new ResponseEntity<>(
            ApiResponse.success("Jobs fetched successfully", response),
            HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponseDTO>> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequestDTO jobRequestDTO) {

        JobResponseDTO response = jobService.updateJob(id, jobRequestDTO);
        return new ResponseEntity<>(
            ApiResponse.success("Job updated successfully", response),
            HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<JobResponseDTO>> updateJobStatus(
            @PathVariable Long id,
            @RequestParam JobStatus status) {

        JobResponseDTO response = jobService.updateJobStatus(id, status);
        return new ResponseEntity<>(
            ApiResponse.success("Job status updated successfully", response),
            HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteJob(
            @PathVariable Long id) {

        jobService.deleteJob(id);
        return new ResponseEntity<>(
            ApiResponse.success("Job deleted successfully"),
            HttpStatus.NO_CONTENT);
    }
}