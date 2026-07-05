package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.CandidateRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.CandidateResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Gender;

public interface CandidateService {

	CandidateResponseDTO createCandidate(CandidateRequestDTO candidateRequestDTO);
	
	CandidateResponseDTO getCandidateById(Long id);
	
	CandidateResponseDTO getCandidateByEmail(String email);
	
	CandidateResponseDTO getCandidateByUserId(Long userId);
	
	List<CandidateResponseDTO> getAllCandidates();
	
	List<CandidateResponseDTO> getAllActiveCandidates();
	
	List<CandidateResponseDTO> getAllInactiveCandidates();
	
	List<CandidateResponseDTO> getCandidatesByLocation(String Location);
	
	List<CandidateResponseDTO> getCandidatesByGender(Gender gender);
	
	List<CandidateResponseDTO> getCandidatesByMaxExperience(int years);
	
	List<CandidateResponseDTO> getCandidatesByMinExperience(int years);
	
	CandidateResponseDTO updateCandidate(Long id, CandidateRequestDTO candidateRequestDTO);
	
	void deactivateCandidate(Long id);
	
	void deleteCandidate(Long id);
	
	
}
