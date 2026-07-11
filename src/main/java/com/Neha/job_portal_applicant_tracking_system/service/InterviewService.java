package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.InterviewRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.InterviewResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewMode;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewResult;


public interface InterviewService {

    InterviewResponseDTO scheduleInterview(InterviewRequestDTO interviewRequestDTO);

    InterviewResponseDTO getInterviewById(Long id);

    List<InterviewResponseDTO> getInterviewsByApplication(Long applicationId);

    List<InterviewResponseDTO> getInterviewsByCandidate(Long candidateId);

    List<InterviewResponseDTO> getInterviewsByJob(Long jobId);

    List<InterviewResponseDTO> getInterviewsByResult(InterviewResult result);

    List<InterviewResponseDTO> getInterviewsByMode(InterviewMode mode);

    InterviewResponseDTO updateInterviewResult(Long id, InterviewResult result,String feedback);
    
    InterviewResponseDTO rescheduleInterview(Long id,InterviewRequestDTO interviewRequestDTO);

    void deleteInterview(Long id);
}