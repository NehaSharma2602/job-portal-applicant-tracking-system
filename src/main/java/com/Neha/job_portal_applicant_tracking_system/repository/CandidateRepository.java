package com.Neha.job_portal_applicant_tracking_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Neha.job_portal_applicant_tracking_system.entity.Candidate;
import com.Neha.job_portal_applicant_tracking_system.entity.Gender;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    // Check duplicate email before saving
    boolean existsByEmail(String email);

    // Check if user is already linked to a candidate
    // Prevents one user from having two candidate profiles
    boolean existsByUserId(Long userId);

    // Find candidate by email
    Optional<Candidate> findByEmail(String email);

    // Find candidate by linked user ID
    Optional<Candidate> findByUserId(Long userId);

    // Find all active candidates
    List<Candidate> findByActiveTrue();

    // Find all inactive candidates
    List<Candidate> findByActiveFalse();

    // Find candidates by location
    List<Candidate> findByCurrentLocation(String currentLocation);

    // Find candidates by gender
    List<Candidate> findByGender(Gender gender);

    // Find candidates with experience less than or equal to given value
    List<Candidate> findByExperienceYearsLessThanEqual(int experienceYears);

    // Find candidates with experience greater than or equal to given value
    List<Candidate> findByExperienceYearsGreaterThanEqual(int experienceYears);
}
