package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.JobRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.JobResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;

public interface JobService {


    JobResponseDTO createJob(JobRequestDTO jobRequestDTO);

    JobResponseDTO getJobById(Long id);

    List<JobResponseDTO> getAllJobs();

    List<JobResponseDTO> getJobsByCompany(Long companyId);

    List<JobResponseDTO> getJobsByStatus(JobStatus status);

    List<JobResponseDTO> getJobsByType(JobType jobType);

    List<JobResponseDTO> getJobsByLocation(String location);

    List<JobResponseDTO> getJobsByCompanyAndStatus(Long companyId, JobStatus status);

    List<JobResponseDTO> getJobsByMaxExperience(int experienceRequired);

    JobResponseDTO updateJob(Long id, JobRequestDTO jobRequestDTO);

    JobResponseDTO updateJobStatus(Long id, JobStatus status);

    void deleteJob(Long id);
}
