package com.Neha.job_portal_applicant_tracking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Neha.job_portal_applicant_tracking_system.entity.Job;
import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;

/**
 * Repository interface for Job database operations.
 * Extends JpaRepository to get built-in CRUD methods like save, findById, findAll, delete and existsById.
 * Custom query methods follow Spring Data JPA naming conventions.
 */
public interface JobRepository extends JpaRepository<Job, Long>{

	/**
     * Checks if a job with the same title already exists in the same company.
     * Used during job creation and update to prevent duplicate job postings within a company.
     *
     * @param title     the job title to check
     * @param companyId the ID of the company
     * @return true if duplicate exists, false otherwise
     */
    boolean existsByTitleAndCompanyId(String title, Long companyId);

    /**
     * Retrieves all jobs posted by a specific company.
     *
     * @param companyId the ID of the company
     * @return List of jobs for that company
     */
    List<Job> findByCompanyId(Long companyId);

    /**
     * Retrieves all jobs filtered by status. e.g. get all OPEN jobs for job listing page.
     *
     * @param status the job status e.g. OPEN, CLOSED, FILLED
     * @return List of jobs with that status
     */
    List<Job> findByStatus(JobStatus status);

    /**
     * Retrieves all jobs filtered by job type. e.g. get all REMOTE or FULL_TIME jobs.
     *
     * @param jobType the job type e.g. FULL_TIME, REMOTE
     * @return List of jobs with that type
     */
    List<Job> findByJobType(JobType jobType);

    /**
     * Retrieves all jobs filtered by location.
     *
     * @param location the location name e.g. "Mumbai", "Delhi"
     * @return List of jobs in that location
     */
    List<Job> findByLocation(String location);

    /**
     * Retrieves all jobs for a specific company filtered by status. e.g. get all OPEN jobs of Google.
     *
     * @param companyId the ID of the company
     * @param status    the job status
     * @return List of jobs matching both filters
     */
    List<Job> findByCompanyIdAndStatus(Long companyId, JobStatus status);

    /**
     * Retrieves all jobs where experience required is less than or equal to the given value.
     * Used by candidates to find jobs they qualify for.
     *
     * @param experienceRequired maximum experience in years
     * @return List of jobs within that experience range
     */
    List<Job> findByExperienceRequiredLessThanEqual(int experienceRequired);
	
	
}
