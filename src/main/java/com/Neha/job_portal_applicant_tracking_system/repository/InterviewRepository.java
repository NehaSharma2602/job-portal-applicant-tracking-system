package com.Neha.job_portal_applicant_tracking_system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Neha.job_portal_applicant_tracking_system.entity.Interview;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewMode;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewResult;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    // Get all interviews for an application
    List<Interview> findByApplicationId(Long applicationId);

    // Get all interviews by result
    List<Interview> findByResult(InterviewResult result);

    // Get all interviews by mode
    List<Interview> findByMode(InterviewMode mode);

    // Get all interviews for a candidate via application
    List<Interview> findByApplicationCandidateId(Long candidateId);

    // Get all interviews for a job via application
    List<Interview> findByApplicationJobId(Long jobId);

    // Check if interview already scheduled at same time for same application
    boolean existsByApplicationIdAndScheduledAt(Long applicationId,LocalDateTime scheduledAt);

    // Get all interviews scheduled after a given time
    List<Interview> findByScheduledAtAfter(LocalDateTime dateTime);

    // Get all interviews scheduled before a given time
    List<Interview> findByScheduledAtBefore(LocalDateTime dateTime);
}