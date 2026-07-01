package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.JobRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.JobResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;

/**
 * Service interface for managing Job operations.
 * Handles business logic for creating, retrieving,
 * updating, and deleting jobs.
 */
public interface JobService {

    /**
     * Creates a new job under a specific company.
     *
     * @param  jobRequestDTO             the job details from the request body
     * @return JobResponseDTO            the saved job details
     * @throws CompanyNotFoundException  if the company does not exist
     * @throws JobAlreadyExistsException if a job with the same title exists in the company
     */
    JobResponseDTO createJob(JobRequestDTO jobRequestDTO);

    /**
     * Retrieves a job by its ID.
     *
     * @param  id                   the ID of the job
     * @return JobResponseDTO       the job details
     * @throws JobNotFoundException if no job is found with the given ID
     */
    JobResponseDTO getJobById(Long id);

    /**
     * Retrieves all jobs in the system.
     *
     * @return List of JobResponseDTO
     * @throws JobNotFoundException if no jobs exist
     */
    List<JobResponseDTO> getAllJobs();

    /**
     * Retrieves all jobs posted by a specific company.
     *
     * @param  companyId                the ID of the company
     * @return List of JobResponseDTO for that company
     * @throws CompanyNotFoundException if the company does not exist
     * @throws JobNotFoundException     if no jobs exist for that company
     */
    List<JobResponseDTO> getJobsByCompany(Long companyId);

    /**
     * Retrieves all jobs filtered by status.
     *
     * @param  status       the job status e.g. OPEN, CLOSED, FILLED, ON_HOLD
     * @return List of JobResponseDTO with that status
     * @throws JobNotFoundException if no jobs exist with that status
     */
    List<JobResponseDTO> getJobsByStatus(JobStatus status);

    /**
     * Retrieves all jobs filtered by type.
     *
     * @param jobType the job type e.g. FULL_TIME, PART_TIME, REMOTE
     * @return List of JobResponseDTO with that type
     * @throws JobNotFoundException if no jobs exist with that type
     */
    List<JobResponseDTO> getJobsByType(JobType jobType);

    /**
     * Retrieves all jobs filtered by location.
     *
     * @param location the location e.g. "Mumbai", "Delhi"
     * @return List of JobResponseDTO in that location
     * @throws JobNotFoundException if no jobs exist in that location
     */
    List<JobResponseDTO> getJobsByLocation(String location);

    /**
     * Retrieves jobs filtered by both company and status.
     *
     * @param companyId the ID of the company
     * @param status    the job status
     * @return List of JobResponseDTO matching both filters
     * @throws CompanyNotFoundException if the company does not exist
     * @throws JobNotFoundException     if no matching jobs exist
     */
    List<JobResponseDTO> getJobsByCompanyAndStatus(Long companyId, JobStatus status);

    /**
     * Retrieves jobs where experience required is less than or equal to given value.
     * Useful for candidates to find jobs they qualify for.
     *
     * @param experienceRequired maximum years of experience to filter by
     * @return List of JobResponseDTO within that experience range
     * @throws JobNotFoundException if no matching jobs exist
     */
    List<JobResponseDTO> getJobsByMaxExperience(int experienceRequired);

    /**
     * Updates an existing job by ID.
     * Editing is allowed regardless of current job status.
     *
     * @param id            the ID of the job to update
     * @param jobRequestDTO the updated job details
     * @return JobResponseDTO the updated job details
     * @throws JobNotFoundException      if no job is found with the given ID
     * @throws JobAlreadyExistsException if the new title already exists in the same company
     * @throws CompanyNotFoundException  if the new company ID does not exist
     */
    JobResponseDTO updateJob(Long id, JobRequestDTO jobRequestDTO);

    /**
     * Updates only the status of a job.
     *
     * @param id     the ID of the job
     * @param status the new status OPEN, CLOSED, FILLED, ON_HOLD
     * @return JobResponseDTO the updated job details
     * @throws JobNotFoundException if no job is found with the given ID
     */
    JobResponseDTO updateJobStatus(Long id, JobStatus status);

    /**
     * Permanently deletes a job from the system.
     *
     * @param id the ID of the job to delete
     * @throws JobNotFoundException if no job is found with the given ID
     */
    void deleteJob(Long id);
}