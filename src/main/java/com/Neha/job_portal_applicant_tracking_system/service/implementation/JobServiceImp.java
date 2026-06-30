package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.JobRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.JobResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Company;
import com.Neha.job_portal_applicant_tracking_system.entity.Job;
import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.exception.CompanyNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.JobAlreadyExistsException;
import com.Neha.job_portal_applicant_tracking_system.repository.CompanyRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.JobRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobServiceImp {

	private final JobRepository jobRepo;
	
	private final CompanyRepository companyRepo;
	
	//mapper job entity to jobresponsedto
	
	private JobResponseDTO mapToResponseDTO(Job job) {

        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setRequiredSkills(job.getRequiredSkills());
        dto.setExperienceRequired(job.getExperienceRequired());
        dto.setSalary(job.getSalary());
        dto.setLocation(job.getLocation());
        dto.setJobType(job.getJobType());
        dto.setStatus(job.getStatus());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());
        dto.setCompanyName(job.getCompany().getCompanyName());

        return dto;
    }
	
	//create job
	@Override
	@Transactional
	private JobResponseDTO createJob(JobRequestDTO dto) {
		// company exist or not
		Company com = companyRepo.findById(dto.getCompanyId()) .orElseThrow(() -> new CompanyNotFoundException("Company does not exists with id: " + dto.getCompanyId()));
		
		// job already present or not
		boolean exists = jobRepo.existsByTitleAndCompanyId(dto.getTitle(), dto.getCompanyId());
		if(exists) {
			throw new JobAlreadyExistsException("Job with title " + dto.getTitle() +"already exists");
		}
		// create new job
		Job job = new Job();
		job.setTitle(dto.getTitle());
		job.setDescription(dto.getDescription());
        job.setRequiredSkills(dto.getRequiredSkills());
        job.setExperienceRequired(dto.getExperienceRequired());
        job.setSalary(dto.getSalary());
        job.setLocation(dto.getLocation());
        job.setJobType(dto.getJobType());
        
        //status is not given then put it to open
        if(job.getStatus() != null) {
        	job.setStatus(dto.getStatus());
        }
        else {
        	job.setStatus(JobStatus.OPEN);
        }
		
        // save and return
        Job saveJob = jobRepo.save(job);
        return mapToResponseDTO(saveJob);
	}
	
	@Override
    public JobResponseDTO getJobById(Long id) {

        Job job = jobRepo.findById(id)
            .orElseThrow(() -> new JobNotFoundException(
                "Job not found with id: " + id));

        return mapToResponseDTO(job);
    }
}
