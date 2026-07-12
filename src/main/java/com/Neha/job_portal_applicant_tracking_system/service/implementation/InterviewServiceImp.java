package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.InterviewRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.InterviewResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Application;
import com.Neha.job_portal_applicant_tracking_system.entity.ApplicationStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.Interview;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewMode;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewResult;
import com.Neha.job_portal_applicant_tracking_system.exception.ApplicationNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.ApplicationNotShortlistedException;
import com.Neha.job_portal_applicant_tracking_system.exception.CandidateNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.InterviewAlreadyScheduledException;
import com.Neha.job_portal_applicant_tracking_system.exception.InterviewNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.JobNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.repository.ApplicationRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.CandidateRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.InterviewRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.JobRepository;
import com.Neha.job_portal_applicant_tracking_system.service.InterviewService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InterviewServiceImp implements InterviewService {

	private final InterviewRepository interviewRepo;
	private final CandidateRepository candidateRepo;
	private final ApplicationRepository applicationRepo;
	private final JobRepository jobRepo;
	
	//==================mapper - Interview Entity -> InterviewResponseDTO =============================//
	private InterviewResponseDTO mapToResponseDTO(Interview interview) {
		InterviewResponseDTO dto = new InterviewResponseDTO();
		
		//Interview fields
		dto.setId(interview.getId());
		dto.setScheduledAt(interview.getScheduledAt());
		dto.setMode(interview.getMode());
		dto.setLocation(interview.getLocation());
		dto.setMeetingLink(interview.getMeetingLink());
		dto.setFeedback(interview.getFeedback());
		dto.setResult(interview.getResult());
		dto.setCreatedAt(interview.getCreatedAt());
		dto.setUpdatedAt(interview.getUpdatedAt());
		
		
		//Application fields
		dto.setApplicationId(interview.getApplication().getId());
		dto.setApplicationStatus(interview.getApplication().getStatus().toString());
		
		
		//Candidate fields via Application
		dto.setCandidateId(interview.getApplication().getCandidate().getId());
		dto.setCandidateFirstName(interview.getApplication().getCandidate().getFirstName());
		dto.setCandidateLastName(interview.getApplication().getCandidate().getLastName());
		dto.setCandidateEmail(interview.getApplication().getCandidate().getEmail());
		
		
		//Job fields via Application
		dto.setJobId(interview.getApplication().getJob().getId());
		dto.setJobTitle(interview.getApplication().getJob().getTitle());
		
		
		//Campanyname via job via application
		dto.setCompanyName(interview.getApplication().getJob().getCompany().getCompanyName());
		
		return dto;
	}

	
	//============================= scheduling interview ====================================//
	@Override
	@Transactional
	public InterviewResponseDTO scheduleInterview(InterviewRequestDTO interviewRequestDTO) {
		//find the application id not found then throw error
		Application application = applicationRepo.findById(interviewRequestDTO.getApplicationId()).orElseThrow(
				() -> new ApplicationNotFoundException("Application not found with id: " + interviewRequestDTO.getApplicationId()));
		
		//whether application is short listed or not
		if(application.getStatus() != ApplicationStatus.SHORTLISTED) {
			throw new ApplicationNotShortlistedException("Interview can only be scheduled for ShortListed application." + "Current status: " + application.getStatus());

		}
		
		//check no interview already scheduled at same time
		boolean alreadyScheduled = interviewRepo.existsByApplicationIdAndScheduledAt(interviewRequestDTO.getApplicationId(), interviewRequestDTO.getScheduledAt());
		if(alreadyScheduled) {
			throw new InterviewAlreadyScheduledException("An interview is already scheduled at: " + interviewRequestDTO.getScheduledAt() + "for this application");
		}
		
		//create new interview
		Interview interview = new Interview();
		interview.setApplication(application);
		interview.setScheduledAt(interviewRequestDTO.getScheduledAt());
		interview.setMode(interviewRequestDTO.getMode());
		interview.setLocation(interviewRequestDTO.getLocation());
		interview.setMeetingLink(interviewRequestDTO.getMeetingLink());
		interview.setFeedback(interviewRequestDTO.getFeedback());
		interview.setResult(InterviewResult.PENDING);
		
		//save and return
		Interview savedInterview = interviewRepo.save(interview);
		return mapToResponseDTO(savedInterview);
	}
	
	//======================= Get Interview By Id ===================================//
	@Override
	public InterviewResponseDTO getInterviewById(Long id) {
		
		Interview interview = interviewRepo.findById(id).orElseThrow(
				() -> new InterviewNotFoundException("Interview not found with id: " + id));
		
		return mapToResponseDTO(interview);
	}
	
	
	//========================Get Interviews By Application ============================//
	@Override
	public List<InterviewResponseDTO> getInterviewsByApplication(Long applicationId){
		
		//confrim application exits
		boolean applicationExists = applicationRepo.existsById(applicationId);
		if(!applicationExists) {
			throw new ApplicationNotFoundException("Application not found with id: " + applicationId);
		}
		
		//get interviews
		List<Interview> interviews = interviewRepo.findByApplicationJobId(applicationId);
		if(interviews.isEmpty()) {
			throw new InterviewNotFoundException("No interviews found for applicationId: " + applicationId);
		}
		
		//convert to dtos
		List<InterviewResponseDTO> response = new ArrayList<>();
		for(Interview interview : interviews) {
			InterviewResponseDTO dto = mapToResponseDTO(interview);
			response.add(dto);
		}
		return response;
	}
	
	//============================ Get Interviews By Candidate ====================================//
	@Override
	public List<InterviewResponseDTO> getInterviewsByCandidate(Long candidateId){
		
		//confim candidate exists
		boolean candidateExists = candidateRepo.existsById(candidateId);
		if(candidateExists) {
			throw new CandidateNotFoundException("Candidate not found with id: " + candidateId);
		}
		
		//get interviews
		List<Interview> interviews = interviewRepo.findByApplicationCandidateId(candidateId);
		if(interviews.isEmpty()) {
			throw new InterviewNotFoundException("No interviews found for candidateId: " + candidateId);
		}
		
		//Convert to dtos
		List<InterviewResponseDTO> response = new ArrayList<>();
		for(Interview interview : interviews) {
			InterviewResponseDTO dto = mapToResponseDTO(interview);
			response.add(dto);
		}
		
		return response;
	}
	
	
	//============================= Get Interviews By Job =========================================//
	@Override
	public List<InterviewResponseDTO> getInterviewsByJob(Long jobId){
		//whether job exists or not
		boolean jobExists = jobRepo.existsById(jobId);
		if(jobExists) {
			throw new JobNotFoundException("Job not found with id: " + jobId);
		}
		
		//get interviews
		List<Interview> interviews = interviewRepo.findByApplicationJobId(jobId);
		if(interviews.isEmpty()) {
			throw new InterviewNotFoundException("No interviews found for jobId: " + jobId);
		}
		
		//convert to DTO
		List<InterviewResponseDTO> response = new ArrayList<>();
		for(Interview interview : interviews) {
			InterviewResponseDTO dto = mapToResponseDTO(interview);
			response.add(dto);
		}
		return response;
	}
	
	//=====================================Get Interviews By Result =========================================//
	@Override
	public List<InterviewResponseDTO> getInterviewsByResult(InterviewResult result){
		List<Interview> interviews = interviewRepo.findByResult(result);
		
		if(interviews.isEmpty()) {
			throw new InterviewNotFoundException("No interviews found with result: " + result);
		}
		
		List<InterviewResponseDTO> response = new ArrayList<>();
		for(Interview interview : interviews) {
			InterviewResponseDTO dto = mapToResponseDTO(interview);
			response.add(dto);
		}
		return response;
	}
	
	//===================================== Get Interviews By Mode ===========================================//
	@Override
	public List<InterviewResponseDTO> getInterviewsByMode(InterviewMode mode){
		List<Interview> interviews = interviewRepo.findByMode(mode);
		
		if(interviews.isEmpty()) {
			throw new InterviewNotFoundException("No interviews found with mode: " + mode);
		}
		
		List<InterviewResponseDTO> response = new ArrayList<>();
		for(Interview interview : interviews) {
			InterviewResponseDTO dto = mapToResponseDTO(interview);
			response.add(dto);
		}
		
		return response;
	}
	
	//===================================== Update Interview Result and Feedback =============================//
	@Override
	@Transactional
	public InterviewResponseDTO updateInterviewResult(Long id, InterviewResult result, String feedback) {
		//find interview
		Interview interview = interviewRepo.findById(id).orElseThrow(
				() -> new InterviewNotFoundException("Interview not found with id: " + id));
		
		//update result and feedback
		interview.setResult(result);
		interview.setFeedback(feedback);
		interview.setUpdatedAt(LocalDateTime.now());
		
		// Step 3 — Save and return
        Interview updatedInterview = interviewRepo.save(interview);
        return mapToResponseDTO(updatedInterview);
	}
	
	
	//======================================= Reschedule Interview =========================================//
	@Override
	@Transactional
	public InterviewResponseDTO rescheduleInterview(Long id, InterviewRequestDTO dto) {
		//find interview
		Interview interview = interviewRepo.findById(id).orElseThrow(
				() -> new InterviewNotFoundException("Interview not found with id: " + id));
		
		//check new time is not already taken
		boolean alreadyScheduled = interviewRepo.existsByApplicationIdAndScheduledAt(interview.getApplication().getId(), dto.getScheduledAt());
		
		if(alreadyScheduled) {
			throw new InterviewAlreadyScheduledException("An interview is already scheduled at: " + dto.getScheduledAt() + "for this application");
		}
		
		//updates fields
		interview.setScheduledAt(dto.getScheduledAt());
		interview.setMode(dto.getMode());
		interview.setLocation(dto.getLocation());
		interview.setMeetingLink(dto.getMeetingLink());
		interview.setUpdatedAt(LocalDateTime.now());
		
		//save and return
		Interview updatedInterview = interviewRepo.save(interview);
		return mapToResponseDTO(updatedInterview);
	}
	
	//=========================================== Delete Interview ============================================//
	@Override
	@Transactional
	public void deleteInterview(Long id) {
		
		Interview interview = interviewRepo.findById(id).orElseThrow(
				() -> new InterviewNotFoundException("Interview not found with id: " + id));
		
		interviewRepo.delete(interview);
	}
}
