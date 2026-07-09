package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.ApplicationRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.ApplicationResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.ApplicationStatus;

/**
 * Service interface for managing Application operations.
 * Handles business logic for applying to jobs, retrieving
 * and updating application status.
 */
public interface ApplicationService {

    /**
     * Submits a new job application.
     *
     * @param applicationRequestDTO the application details
     * @return ApplicationResponseDTO the saved application details
     * @throws CandidateNotFoundException         if candidate does not exist
     * @throws JobNotFoundException               if job does not exist
     * @throws ResumeNotFoundException            if resume does not exist
     * @throws DuplicateApplicationException      if candidate already applied to this job
     * @throws JobNotOpenForApplicationException  if job is not OPEN
     */
    ApplicationResponseDTO applyForJob(ApplicationRequestDTO applicationRequestDTO);

    /**
     * Retrieves an application by its ID.
     *
     * @param id the ID of the application
     * @return ApplicationResponseDTO the application details
     * @throws ApplicationNotFoundException if no application found with given ID
     */
    ApplicationResponseDTO getApplicationById(Long id);

    /**
     * Retrieves all applications submitted by a candidate.
     *
     * @param candidateId the ID of the candidate
     * @return List of ApplicationResponseDTO for that candidate
     * @throws CandidateNotFoundException   if candidate does not exist
     * @throws ApplicationNotFoundException if no applications found
     */
    List<ApplicationResponseDTO> getApplicationsByCandidate(Long candidateId);

    /**
     * Retrieves all applications for a specific job.
     *
     * @param jobId the ID of the job
     * @return List of ApplicationResponseDTO for that job
     * @throws JobNotFoundException         if job does not exist
     * @throws ApplicationNotFoundException if no applications found
     */
    List<ApplicationResponseDTO> getApplicationsByJob(Long jobId);

    /**
     * Retrieves all applications filtered by status.
     *
     * @param status the application status
     * @return List of ApplicationResponseDTO with that status
     * @throws ApplicationNotFoundException if no applications found
     */
    List<ApplicationResponseDTO> getApplicationsByStatus(ApplicationStatus status);

    /**
     * Retrieves all applications by candidate filtered by status.
     *
     * @param candidateId the ID of the candidate
     * @param status      the application status
     * @return List of ApplicationResponseDTO
     * @throws ApplicationNotFoundException if no applications found
     */
    List<ApplicationResponseDTO> getApplicationsByCandidateAndStatus(
            Long candidateId, ApplicationStatus status);

    /**
     * Retrieves all applications for a job filtered by status.
     * e.g. get all SHORTLISTED candidates for a job.
     *
     * @param jobId  the ID of the job
     * @param status the application status
     * @return List of ApplicationResponseDTO
     * @throws ApplicationNotFoundException if no applications found
     */
    List<ApplicationResponseDTO> getApplicationsByJobAndStatus(
            Long jobId, ApplicationStatus status);

    /**
     * Updates the status of an application.
     * Used by recruiter to move application through the pipeline.
     *
     * @param id     the ID of the application
     * @param status the new status
     * @return ApplicationResponseDTO the updated application
     * @throws ApplicationNotFoundException if no application found with given ID
     */
    ApplicationResponseDTO updateApplicationStatus(Long id, ApplicationStatus status);
}