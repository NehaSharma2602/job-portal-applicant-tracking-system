package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.ApplicationRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.ApplicationResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Application;
import com.Neha.job_portal_applicant_tracking_system.entity.ApplicationStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.Candidate;
import com.Neha.job_portal_applicant_tracking_system.entity.Job;
import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.Resume;
import com.Neha.job_portal_applicant_tracking_system.exception.ApplicationNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.CandidateNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.DuplicateApplicationException;
import com.Neha.job_portal_applicant_tracking_system.exception.JobNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.JobNotOpenForApplicationException;
import com.Neha.job_portal_applicant_tracking_system.exception.ResumeNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.repository.ApplicationRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.CandidateRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.JobRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.ResumeRepository;
import com.Neha.job_portal_applicant_tracking_system.service.ApplicationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplicationServiceImp implements ApplicationService {

    private final ApplicationRepository applicationRepo;
    private final CandidateRepository candidateRepo;
    private final JobRepository jobRepo;
    private final ResumeRepository resumeRepo;
    
    
    
 // MAPPER — Application Entity → ApplicationResponseDTO
    private ApplicationResponseDTO mapToResponseDTO(Application application) {

        ApplicationResponseDTO dto = new ApplicationResponseDTO();

        // Application fields
        dto.setId(application.getId());
        dto.setApplicationDate(application.getApplicationDate());
        dto.setStatus(application.getStatus());
        dto.setCoverLetter(application.getCoverLetter());
        dto.setCreatedAt(application.getCreatedAt());
        dto.setUpdatedAt(application.getUpdatedAt());

        // Candidate fields
        dto.setCandidateId(application.getCandidate().getId());
        dto.setCandidateFirstName(application.getCandidate().getFirstName());
        dto.setCandidateLastName(application.getCandidate().getLastName());
        dto.setCandidateEmail(application.getCandidate().getEmail());

        // Job fields
        dto.setJobId(application.getJob().getId());
        dto.setJobTitle(application.getJob().getTitle());
        dto.setJobLocation(application.getJob().getLocation());

        // Company name via Job
        dto.setCompanyName(application.getJob().getCompany().getCompanyName());

        // Resume fields
        dto.setResumeId(application.getResume().getId());
        dto.setResumeFileName(application.getResume().getFileName());

        return dto;
    }

    //===============apply for job
    @Override
    @Transactional
    public ApplicationResponseDTO applyForJob(ApplicationRequestDTO dto) {

        //Find candidate, throw exception if not found
        Candidate candidate = candidateRepo.findById(dto.getCandidateId()).orElseThrow(
        		() -> new CandidateNotFoundException("Candidate not found with id: " + dto.getCandidateId()));

        //Find job, throw exception if not found
        Job job = jobRepo.findById(dto.getJobId()).orElseThrow(
        		() -> new JobNotFoundException("Job not found with id: " + dto.getJobId()));

        //Check if job is OPEN for applications
        if (job.getStatus() != JobStatus.OPEN) {
            throw new JobNotOpenForApplicationException("Job is not open for applications. Current status: " + job.getStatus());
        }

        //Prevent duplicate application
        boolean alreadyApplied = applicationRepo.existsByCandidateIdAndJobId(dto.getCandidateId(), dto.getJobId());
        if (alreadyApplied) {
            throw new DuplicateApplicationException("Candidate has already applied for this job");
        }

        //Find resume, throw exception if not found
        Resume resume = resumeRepo.findById(dto.getResumeId()).orElseThrow(
        		() -> new ResumeNotFoundException("Resume not found with id: " + dto.getResumeId()));

        //Create new Application entity and set fields
        Application application = new Application();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setResume(resume);
        application.setCoverLetter(dto.getCoverLetter());
        application.setStatus(ApplicationStatus.APPLIED);

        //— Save and return
        Application savedApplication = applicationRepo.save(application);
        return mapToResponseDTO(savedApplication);
    }

    //================= get application by id 
    @Override
    public ApplicationResponseDTO getApplicationById(Long id) {

        Application application = applicationRepo.findById(id).orElseThrow(
        		() -> new ApplicationNotFoundException("Application not found with id: " + id));

        return mapToResponseDTO(application);
    }

    //=================== get application by candidate
    @Override
    public List<ApplicationResponseDTO> getApplicationsByCandidate(Long candidateId) {

        //Confirm candidate exists
        boolean candidateExists = candidateRepo.existsById(candidateId);
        if (!candidateExists) {
            throw new CandidateNotFoundException("Candidate not found with id: " + candidateId);
        }

        //Get applications
        List<Application> applications =applicationRepo.findByCandidateId(candidateId);

        if (applications.isEmpty()) {
            throw new ApplicationNotFoundException("No applications found for candidateId: " + candidateId);
        }

        //Convert to DTOs
        List<ApplicationResponseDTO> responseDTOs = new ArrayList<>();
        for (Application application : applications) {
            ApplicationResponseDTO dto = mapToResponseDTO(application);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }


    //=============== get applications by job 
    @Override
    public List<ApplicationResponseDTO> getApplicationsByJob(Long jobId) {

        //Confirm job exists
        boolean jobExists = jobRepo.existsById(jobId);
        if (!jobExists) {
            throw new JobNotFoundException("Job not found with id: " + jobId);
        }

        //Get applications
        List<Application> applications = applicationRepo.findByJobId(jobId);

        if (applications.isEmpty()) {
            throw new ApplicationNotFoundException("No applications found for jobId: " + jobId);
        }

        //Convert to DTOs
        List<ApplicationResponseDTO> responseDTOs = new ArrayList<>();
        for (Application application : applications) {
            ApplicationResponseDTO dto = mapToResponseDTO(application);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    //================== get application by status 
    @Override
    public List<ApplicationResponseDTO> getApplicationsByStatus(ApplicationStatus status) {

        List<Application> applications = applicationRepo.findByStatus(status);

        if (applications.isEmpty()) {
            throw new ApplicationNotFoundException("No applications found with status: " + status);
        }

        List<ApplicationResponseDTO> responseDTOs = new ArrayList<>();
        for (Application application : applications) {
            ApplicationResponseDTO dto = mapToResponseDTO(application);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    //==================get application by candidate and status 
    @Override
    public List<ApplicationResponseDTO> getApplicationsByCandidateAndStatus(
            Long candidateId, ApplicationStatus status) {

        boolean candidateExists = candidateRepo.existsById(candidateId);
        if (!candidateExists) {
            throw new CandidateNotFoundException("Candidate not found with id: " + candidateId);
        }

        List<Application> applications = applicationRepo.findByCandidateIdAndStatus(candidateId, status);

        if (applications.isEmpty()) {
            throw new ApplicationNotFoundException("No applications found for candidateId: " + candidateId + " with status: " + status);
        }

        List<ApplicationResponseDTO> responseDTOs = new ArrayList<>();
        for (Application application : applications) {
            ApplicationResponseDTO dto = mapToResponseDTO(application);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }


    //===================== get applicationss by job and status
    @Override
    public List<ApplicationResponseDTO> getApplicationsByJobAndStatus(Long jobId, ApplicationStatus status) {

        boolean jobExists = jobRepo.existsById(jobId);
        if (!jobExists) {
            throw new JobNotFoundException("Job not found with id: " + jobId);
        }

        List<Application> applications = applicationRepo.findByJobIdAndStatus(jobId, status);

        if (applications.isEmpty()) {
            throw new ApplicationNotFoundException("No applications found for jobId: " + jobId + " with status: " + status);
        }

        List<ApplicationResponseDTO> responseDTOs = new ArrayList<>();
        for (Application application : applications) {
            ApplicationResponseDTO dto = mapToResponseDTO(application);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    //====================== update application status
    @Override
    @Transactional
    public ApplicationResponseDTO updateApplicationStatus(Long id, ApplicationStatus status) {

        //Find application
        Application application = applicationRepo.findById(id).orElseThrow(
        		() -> new ApplicationNotFoundException("Application not found with id: " + id));

        //Update status and timestamp
        application.setStatus(status);
        application.setUpdatedAt(LocalDateTime.now());

        //Save and return
        Application updatedApplication = applicationRepo.save(application);
        return mapToResponseDTO(updatedApplication);
    }

    
}
