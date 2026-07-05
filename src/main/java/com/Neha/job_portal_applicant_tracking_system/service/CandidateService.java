package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.CandidateRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.CandidateResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Gender;

/**
 * Service interface for managing Candidate operations.
 * Handles business logic for registering, retrieving,
 * updating, and deleting candidates.
 */
public interface CandidateService {

    /**
     * Registers a new candidate in the system.
     *
     * @param candidateRequestDTO the candidate details from the request body
     * @return CandidateResponseDTO the saved candidate details
     * @throws CandidateAlreadyExistsException        if email is already registered
     * @throws UserAlreadyLinkedToACandidateException if user already has a candidate profile
     * @throws ResourceNotFoundException              if the provided userId does not exist
     */
    CandidateResponseDTO createCandidate(CandidateRequestDTO candidateRequestDTO);

    /**
     * Retrieves a candidate by their ID.
     *
     * @param id the ID of the candidate
     * @return CandidateResponseDTO the candidate details
     * @throws CandidateNotFoundException if no candidate found with given ID
     */
    CandidateResponseDTO getCandidateById(Long id);

    /**
     * Retrieves a candidate by their email.
     *
     * @param email the email of the candidate
     * @return CandidateResponseDTO the candidate details
     * @throws CandidateNotFoundException if no candidate found with given email
     */
    CandidateResponseDTO getCandidateByEmail(String email);

    /**
     * Retrieves a candidate linked to a specific user.
     *
     * @param userId the ID of the user
     * @return CandidateResponseDTO the candidate details
     * @throws CandidateNotFoundException if no candidate linked to given userId
     */
    CandidateResponseDTO getCandidateByUserId(Long userId);

    /**
     * Retrieves all candidates in the system.
     *
     * @return List of CandidateResponseDTO
     * @throws CandidateNotFoundException if no candidates exist
     */
    List<CandidateResponseDTO> getAllCandidates();

    /**
     * Retrieves all active candidates.
     *
     * @return List of CandidateResponseDTO where active = true
     * @throws CandidateNotFoundException if no active candidates exist
     */
    List<CandidateResponseDTO> getAllActiveCandidates();

    /**
     * Retrieves all inactive candidates.
     *
     * @return List of CandidateResponseDTO where active = false
     * @throws CandidateNotFoundException if no inactive candidates exist
     */
    List<CandidateResponseDTO> getAllInactiveCandidates();

    /**
     * Retrieves candidates filtered by location.
     *
     * @param location the location to filter by e.g. "Mumbai"
     * @return List of CandidateResponseDTO in that location
     * @throws CandidateNotFoundException if no candidates found in that location
     */
    List<CandidateResponseDTO> getCandidatesByLocation(String location);

    /**
     * Retrieves candidates filtered by gender.
     *
     * @param gender the gender to filter by e.g. MALE, FEMALE, OTHER
     * @return List of CandidateResponseDTO with that gender
     * @throws CandidateNotFoundException if no candidates found with that gender
     */
    List<CandidateResponseDTO> getCandidatesByGender(Gender gender);

    /**
     * Retrieves candidates whose experience is less than or equal to given value.
     * Useful for finding junior or fresher candidates.
     *
     * @param years maximum years of experience to filter by
     * @return List of CandidateResponseDTO within that experience range
     * @throws CandidateNotFoundException if no candidates found
     */
    List<CandidateResponseDTO> getCandidatesByMaxExperience(int years);

    /**
     * Retrieves candidates whose experience is greater than or equal to given value.
     * Useful for finding senior candidates.
     *
     * @param years minimum years of experience to filter by
     * @return List of CandidateResponseDTO within that experience range
     * @throws CandidateNotFoundException if no candidates found
     */
    List<CandidateResponseDTO> getCandidatesByMinExperience(int years);

    /**
     * Updates an existing candidate's details.
     *
     * @param id                  the ID of the candidate to update
     * @param candidateRequestDTO the updated candidate details
     * @return CandidateResponseDTO the updated candidate details
     * @throws CandidateNotFoundException      if no candidate found with given ID
     * @throws CandidateInactiveException      if candidate is deactivated
     * @throws CandidateAlreadyExistsException if new email is already taken
     */
    CandidateResponseDTO updateCandidate(Long id, CandidateRequestDTO candidateRequestDTO);

    /**
     * Deactivates a candidate account — sets active = false.
     * The candidate still exists in the database (soft delete).
     *
     * @param id the ID of the candidate to deactivate
     * @throws CandidateNotFoundException if no candidate found with given ID
     * @throws CandidateInactiveException if candidate is already deactivated
     */
    void deactivateCandidate(Long id);

    /**
     * Permanently deletes a candidate from the system.
     *
     * @param id the ID of the candidate to delete
     * @throws CandidateNotFoundException if no candidate found with given ID
     */
    void deleteCandidate(Long id);
}