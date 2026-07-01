package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.JobRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.JobResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Company;
import com.Neha.job_portal_applicant_tracking_system.entity.Job;
import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;
import com.Neha.job_portal_applicant_tracking_system.exception.CompanyNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.JobAlreadyExistsException;
import com.Neha.job_portal_applicant_tracking_system.exception.JobNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.repository.CompanyRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.JobRepository;
import com.Neha.job_portal_applicant_tracking_system.service.JobService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobServiceImp implements JobService{

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
	public JobResponseDTO createJob(JobRequestDTO dto) {
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
	
	// all the jobs
	 @Override
	    public List<JobResponseDTO> getAllJobs() {

	        List<Job> jobs = jobRepo.findAll();

	        if (jobs.isEmpty()) {
	            throw new JobNotFoundException("No jobs found in the system");
	        }

	        List<JobResponseDTO> responseDTOs = new ArrayList<>();
	        for (Job job : jobs) {
	            JobResponseDTO dto = mapToResponseDTO(job);
	            responseDTOs.add(dto);
	        }

	        return responseDTOs;
	    }
	 
	 //by id
	@Override
    public JobResponseDTO getJobById(Long id) {

        Job job = jobRepo.findById(id)
            .orElseThrow(() -> new JobNotFoundException(
                "Job not found with id: " + id));

        return mapToResponseDTO(job);
    }
	
	//get jobs by company
	@Override
	public List<JobResponseDTO> getJobsByCompany(Long companyId){
		
		//company exists or not
		boolean exists = jobRepo.existsById(companyId);
		if(! exists) {
			throw new CompanyNotFoundException("Company not found with id: " + companyId);
		}
		// get all the jobs from company
		List<Job> job = jobRepo.findByCompanyId(companyId);
		if(job.isEmpty()) {
			throw new JobNotFoundException("no job found for the company id: "+ companyId);
		}
		
		List<JobResponseDTO> dto = new ArrayList<>();
		for(Job j1 : job) {
			JobResponseDTO response = mapToResponseDTO(j1);
			dto.add(response);
		}
		return dto;
	}
	
	//by status
	@Override
	public List<JobResponseDTO> getJobsByStatus(JobStatus status){
		
		List<Job> job = jobRepo.findByStatus(status);
		
		if(job.isEmpty()) {
			throw new JobNotFoundException("Job does not exist: "+ status);
		}
		List<JobResponseDTO> dto = new ArrayList<JobResponseDTO>();
		for(Job j : job) {
			JobResponseDTO response = mapToResponseDTO(j);
			dto.add(response);
		}
		return dto;
	}
	
	//by type
	@Override
    public List<JobResponseDTO> getJobsByType(JobType jobType) {

        List<Job> jobs = jobRepo.findByJobType(jobType);

        if (jobs.isEmpty()) {
            throw new JobNotFoundException("No jobs found with type: " + jobType);
        }

        List<JobResponseDTO> dto = new ArrayList<>();
        for (Job job : jobs) {
            JobResponseDTO response = mapToResponseDTO(job);
            dto.add(response);
        }

        return dto;
    }
	
	@Override
    public List<JobResponseDTO> getJobsByLocation(String location) {

        List<Job> jobs = jobRepo.findByLocation(location);

        if (jobs.isEmpty()) {
            throw new JobNotFoundException("No jobs found in location: " + location);
        }

        List<JobResponseDTO> dto = new ArrayList<>();
        for (Job job : jobs) {
            JobResponseDTO response = mapToResponseDTO(job);
            dto.add(response);
        }

        return dto;
    }
	
	
	//by company id and status
	@Override
	public List<JobResponseDTO> getJobsByCompanyAndStatus(Long companyId, JobStatus status){
		
		boolean exist = companyRepo.existsById(companyId);
		if(! exist) {
			throw new CompanyNotFoundException("Company does not exists: " + companyId);
		}
		
		List<Job> job = jobRepo.findByCompanyIdAndStatus(companyId, status);
		
		if (job.isEmpty()) {
            throw new JobNotFoundException("No jobs found for company id: " + companyId + " with status: " + status);
        }
		
		List<JobResponseDTO> dto = new ArrayList<JobResponseDTO>();
		for(Job j1 : job) {
			JobResponseDTO response = mapToResponseDTO(j1);
			dto.add(response);
		}
		return dto;
	}
	
	//by experience
	@Override
    public List<JobResponseDTO> getJobsByMaxExperience(int experienceRequired) {

        List<Job> jobs = jobRepo.findByExperienceRequiredLessThanEqual(experienceRequired);

        if (jobs.isEmpty()) {
            throw new JobNotFoundException(
                "No jobs found with experience requirement <= " + experienceRequired);
        }

        List<JobResponseDTO> dto = new ArrayList<>();
        for (Job job : jobs) {
            JobResponseDTO response = mapToResponseDTO(job);
            dto.add(response);
        }

        return dto;
    }
	
	//update job with id
	@Override
	@Transactional
	public JobResponseDTO updateJob(Long id, JobRequestDTO dto) {
		//job exists or not
		Job job = jobRepo.findById(id).orElseThrow(
				() -> new JobNotFoundException("Job not found with id: " + id));
		
		//if title is changes then we have to check whether new title is already present in same company or not
		boolean isTitleChanging = job.getTitle().equals(dto.getTitle());
		boolean isTitleAlreadyExist = jobRepo.existsByTitleAndCompanyId(dto.getTitle(), dto.getCompanyId());
		if(isTitleChanging && isTitleAlreadyExist) {
			throw new JobAlreadyExistsException("Job with title '" + dto.getTitle() + "' already exists in this company");
		}
		
		// if the company changes but job exists
		if(job.getCompany().getId().equals(dto.getCompanyId())) {
			Company com = companyRepo.findById(dto.getCompanyId()).orElseThrow(
					() -> new CompanyNotFoundException("Company not found with id: " + dto.getCompanyId()));
			job.setCompany(com);
		}
		job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setRequiredSkills(dto.getRequiredSkills());
        job.setExperienceRequired(dto.getExperienceRequired());
        job.setSalary(dto.getSalary());
        job.setLocation(dto.getLocation());
        job.setJobType(dto.getJobType());
        
        if (dto.getStatus() != null) {
            job.setStatus(dto.getStatus());
        }

        job.setUpdatedAt(LocalDateTime.now());

        Job updatedJob = jobRepo.save(job);
        return mapToResponseDTO(updatedJob);
		
	}
	
	// update status
	@Override
    @Transactional
    public JobResponseDTO updateJobStatus(Long id, JobStatus status) {

        Job job = jobRepo.findById(id)
            .orElseThrow(() -> new JobNotFoundException(
                "Job not found with id: " + id));

        job.setStatus(status);
        job.setUpdatedAt(LocalDateTime.now());

        Job updatedJob = jobRepo.save(job);
        return mapToResponseDTO(updatedJob);
    }
	
	//delete job
	@Override
    @Transactional
    public void deleteJob(Long id) {

        Job job = jobRepo.findById(id)
            .orElseThrow(() -> new JobNotFoundException(
                "Job not found with id: " + id));

        jobRepo.delete(job);
    }

	
}
