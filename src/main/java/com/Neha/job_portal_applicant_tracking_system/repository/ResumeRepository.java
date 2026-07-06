package com.Neha.job_portal_applicant_tracking_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Neha.job_portal_applicant_tracking_system.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

	 // Get all resumes of a candidate
    List<Resume> findByCandidateId(Long candidateId);

    // Get the default resume of a candidate
    Optional<Resume> findByCandidateIdAndIsDefaultTrue(Long candidateId);

    // Check if candidate already has a default resume
    boolean existsByCandidateIdAndIsDefaultTrue(Long candidateId);

    // Check if resume with same fileName exists for a candidate
    boolean existsByCandidateIdAndFileName(Long candidateId, String fileName);

    // Count how many resumes a candidate has
    int countByCandidateId(Long candidateId);
}
