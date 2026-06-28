package com.Neha.job_portal_applicant_tracking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Neha.job_portal_applicant_tracking_system.entity.Job;
import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;

public interface JobRepository extends JpaRepository<Job, Long>{

	// Check duplicate job title within same company
    boolean existsByTitleAndCompanyId(String title, Long companyId);

    // Get all jobs by company
    List<Job> findByCompanyId(Long companyId);

    // Get all jobs by status e.g. OPEN, CLOSED
    List<Job> findByStatus(JobStatus status);

    // Get all jobs by type e.g. FULL_TIME, REMOTE
    List<Job> findByJobType(JobType jobType);

    // Get all jobs by location
    List<Job> findByLocation(String location);

    // Get all jobs by company and status
    List<Job> findByCompanyIdAndStatus(Long companyId, JobStatus status);

    // Get all jobs where experience is less than or equal to given value
    List<Job> findByExperienceRequiredLessThanEqual(int experienceRequired);
	
	
}
