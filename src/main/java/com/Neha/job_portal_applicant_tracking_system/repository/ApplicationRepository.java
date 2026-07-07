package com.Neha.job_portal_applicant_tracking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Neha.job_portal_applicant_tracking_system.entity.Application;
import com.Neha.job_portal_applicant_tracking_system.entity.ApplicationStatus;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Prevent duplicate — check if candidate already applied to same job
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);

    // Get all applications by candidate
    List<Application> findByCandidateId(Long candidateId);

    // Get all applications for a job
    List<Application> findByJobId(Long jobId);

    // Get all applications by status
    List<Application> findByStatus(ApplicationStatus status);

    // Get all applications by candidate and status
    List<Application> findByCandidateIdAndStatus(Long candidateId, ApplicationStatus status);

    // Get all applications for a job and status
    // e.g. all SHORTLISTED candidates for a job
    List<Application> findByJobIdAndStatus(Long jobId, ApplicationStatus status);

    // Count applications for a job
    int countByJobId(Long jobId);

    // Count applications by candidate
    int countByCandidateId(Long candidateId);
}
