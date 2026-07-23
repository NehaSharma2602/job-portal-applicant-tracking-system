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

/**
 * REST Controller for managing Job operations.
 * Exposes endpoints for creating, retrieving, updating and deleting jobs.
 * Base URL — /api/jobs
 */
@RestController
@RequestMapping("/api/jobs")
public class JobController {

	 /** Service layer dependency for job business logic */
    private final JobService jobService;

    /**
     * Constructor injection of JobService.
     * Preferred over field injection for better testability.
     *
     * @param jobService the service handling job business logic
     */
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * Creates a new job under a specific company.
     * Validates request body fields before processing.
     *
     * @param jobRequestDTO the job details from request body
     * @return ApiResponse containing the saved job details with HTTP status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ApiResponse<JobResponseDTO>> createJob(@Valid @RequestBody JobRequestDTO jobRequestDTO) {

        JobResponseDTO response = jobService.createJob(jobRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Job created successfully", response),HttpStatus.CREATED);
    }

    /**
     * Retrieves a job by its ID.
     *
     * @param id the ID of the job to retrieve
     * @return ApiResponse containing the job details with HTTP status 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponseDTO>> getJobById(@PathVariable Long id) {

        JobResponseDTO response = jobService.getJobById(id);
        return new ResponseEntity<>(ApiResponse.success("Job fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all jobs in the system.
     *
     * @return ApiResponse containing list of all jobs with HTTP status 200 OK
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getAllJobs() {

        List<JobResponseDTO> response = jobService.getAllJobs();
        return new ResponseEntity<>(ApiResponse.success("All jobs fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all jobs posted by a specific company.
     *
     * @param companyId the ID of the company
     * @return ApiResponse containing list of jobs for that company with HTTP status 200 OK
     */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByCompany(@PathVariable Long companyId) {

        List<JobResponseDTO> response = jobService.getJobsByCompany(companyId);
        return new ResponseEntity<>(ApiResponse.success("Jobs fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all jobs filtered by status.
     *
     * @param status the job status e.g. OPEN, CLOSED, FILLED
     * @return ApiResponse containing list of jobs with that status with HTTP status 200 OK
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByStatus(@PathVariable JobStatus status) {

        List<JobResponseDTO> response = jobService.getJobsByStatus(status);
        return new ResponseEntity<>(ApiResponse.success("Jobs fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all jobs filtered by job type.
     *
     * @param jobType the job type e.g. FULL_TIME, REMOTE
     * @return ApiResponse containing list of jobs with that type with HTTP status 200 OK
     */
    @GetMapping("/type/{jobType}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByType(@PathVariable JobType jobType) {

        List<JobResponseDTO> response = jobService.getJobsByType(jobType);
        return new ResponseEntity<>(ApiResponse.success("Jobs fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all jobs filtered by location.
     *
     * @param location the location name e.g. "Mumbai", "Delhi"
     * @return ApiResponse containing list of jobs in that location with HTTP status 200 OK
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByLocation(@PathVariable String location) {

        List<JobResponseDTO> response = jobService.getJobsByLocation(location);
        return new ResponseEntity<>(ApiResponse.success("Jobs fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all jobs for a specific company filtered by status. e.g. get all OPEN jobs of a specific company.
     *
     * @param companyId the ID of the company
     * @param status    the job status
     * @return ApiResponse containing list of matching jobs with HTTP status 200 OK
     */
    @GetMapping("/company/{companyId}/status/{status}")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByCompanyAndStatus(@PathVariable Long companyId, @PathVariable JobStatus status) {

        List<JobResponseDTO> response = jobService.getJobsByCompanyAndStatus(companyId, status);
        return new ResponseEntity<>( ApiResponse.success("Jobs fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all jobs where experience required is less than or equal to the given value.
     * Used by candidates to find jobs they qualify for.
     *
     * @param max maximum years of experience to filter by
     * @return ApiResponse containing list of matching jobs with HTTP status 200 OK
     */
    @GetMapping("/experience")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getJobsByMaxExperience(@RequestParam int max) {

        List<JobResponseDTO> response = jobService.getJobsByMaxExperience(max);
        return new ResponseEntity<>(ApiResponse.success("Jobs fetched successfully", response), HttpStatus.OK);
    } 

    /**
     * Updates an existing job by its ID.
     * Editing is allowed regardless of current job status.
     * Validates request body fields before processing.
     *
     * @param id            the ID of the job to update
     * @param jobRequestDTO the updated job details from request body
     * @return ApiResponse containing the updated job details with HTTP status 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponseDTO>> updateJob(@PathVariable Long id, @Valid @RequestBody JobRequestDTO jobRequestDTO) {

        JobResponseDTO response = jobService.updateJob(id, jobRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Job updated successfully", response), HttpStatus.OK);
    }

    /**
     * Updates only the status of a job.
     * Used by recruiter to open, close or fill a job position.
     *
     * @param id     the ID of the job
     * @param status the new job status
     * @return ApiResponse containing the updated job details with HTTP status 200 OK
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<JobResponseDTO>> updateJobStatus(@PathVariable Long id, @RequestParam JobStatus status) {

        JobResponseDTO response = jobService.updateJobStatus(id, status);
        return new ResponseEntity<>(ApiResponse.success("Job status updated successfully", response), HttpStatus.OK);
    }

    /**
     * Permanently deletes a job from the system.
     * This is a hard delete — data cannot be recovered.
     *
     * @param id the ID of the job to delete
     * @return ApiResponse with success message with HTTP status 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteJob(@PathVariable Long id) {

        jobService.deleteJob(id);
        return new ResponseEntity<>(ApiResponse.success("Job deleted successfully"), HttpStatus.NO_CONTENT);
    }
}