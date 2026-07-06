package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.ResumeRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.ResumeResponseDTO;

public interface ResumeService {

	ResumeResponseDTO uploadResume(ResumeRequestDTO resumeRequestDTO);
	
	ResumeResponseDTO getResumeById(Long id);
	
	ResumeResponseDTO getDefaultResume(Long candidateId);
	
	List<ResumeResponseDTO> getResumesByCandidateId(Long candidateId);
	
	ResumeResponseDTO setDefaultResume(Long resumeId, Long candidateId);
	
	void deleteResume(Long id);
}
