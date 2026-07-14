package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.InterviewRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.InterviewResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewMode;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewResult;

/**
 * Service interface for managing Interview operations.
 * Handles business logic for scheduling, retrieving
 * and updating interviews.
 */
public interface InterviewService {

    /**
     * Schedules a new interview for a SHORTLISTED application.
     *
     * @param interviewRequestDTO the interview details
     * @return InterviewResponseDTO the saved interview details
     * @throws ApplicationNotFoundException        if application does not exist
     * @throws ApplicationNotShortlistedException  if application is not SHORTLISTED
     * @throws InterviewAlreadyScheduledException  if interview already scheduled
     *                                             at same time for same application
     */
    InterviewResponseDTO scheduleInterview(InterviewRequestDTO interviewRequestDTO);

    /**
     * Retrieves an interview by its ID.
     *
     * @param id the ID of the interview
     * @return InterviewResponseDTO the interview details
     * @throws InterviewNotFoundException if no interview found with given ID
     */
    InterviewResponseDTO getInterviewById(Long id);

    /**
     * Retrieves all interviews for a specific application.
     *
     * @param applicationId the ID of the application
     * @return List of InterviewResponseDTO for that application
     * @throws ApplicationNotFoundException if application does not exist
     * @throws InterviewNotFoundException   if no interviews found
     */
    List<InterviewResponseDTO> getInterviewsByApplication(Long applicationId);

    /**
     * Retrieves all interviews for a specific candidate.
     *
     * @param candidateId the ID of the candidate
     * @return List of InterviewResponseDTO for that candidate
     * @throws CandidateNotFoundException if candidate does not exist
     * @throws InterviewNotFoundException if no interviews found
     */
    List<InterviewResponseDTO> getInterviewsByCandidate(Long candidateId);

    /**
     * Retrieves all interviews for a specific job.
     *
     * @param jobId the ID of the job
     * @return List of InterviewResponseDTO for that job
     * @throws JobNotFoundException       if job does not exist
     * @throws InterviewNotFoundException if no interviews found
     */
    List<InterviewResponseDTO> getInterviewsByJob(Long jobId);

    /**
     * Retrieves all interviews filtered by result.
     *
     * @param result the interview result e.g. PENDING, PASSED, FAILED
     * @return List of InterviewResponseDTO with that result
     * @throws InterviewNotFoundException if no interviews found
     */
    List<InterviewResponseDTO> getInterviewsByResult(InterviewResult result);

    /**
     * Retrieves all interviews filtered by mode.
     *
     * @param mode the interview mode e.g. ONLINE, OFFLINE, HYBRID
     * @return List of InterviewResponseDTO with that mode
     * @throws InterviewNotFoundException if no interviews found
     */
    List<InterviewResponseDTO> getInterviewsByMode(InterviewMode mode);

    /**
     * Updates the result and feedback of an interview.
     * Used by recruiter after the interview is conducted.
     *
     * @param id       the ID of the interview
     * @param result   the new result PASSED, FAILED, CANCELLED
     * @param feedback the recruiter's feedback
     * @return InterviewResponseDTO the updated interview
     * @throws InterviewNotFoundException if no interview found with given ID
     */
    InterviewResponseDTO updateInterviewResult(Long id, InterviewResult result, String feedback);

    /**
     * Reschedules an existing interview to a new date and time.
     *
     * @param id                  the ID of the interview to reschedule
     * @param interviewRequestDTO the updated interview details
     * @return InterviewResponseDTO the updated interview
     * @throws InterviewNotFoundException         if no interview found with given ID
     * @throws InterviewAlreadyScheduledException if new time is already taken
     */
    InterviewResponseDTO rescheduleInterview(Long id, InterviewRequestDTO interviewRequestDTO);

    /**
     * Deletes an interview permanently.
     *
     * @param id the ID of the interview to delete
     * @throws InterviewNotFoundException if no interview found with given ID
     */
    void deleteInterview(Long id);
}