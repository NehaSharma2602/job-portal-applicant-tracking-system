package com.Neha.job_portal_applicant_tracking_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Neha.job_portal_applicant_tracking_system.dto.JobRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.JobResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;
import com.Neha.job_portal_applicant_tracking_system.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    //================== POST /api/jobs
    @PostMapping
    public ResponseEntity<JobResponseDTO> createJob( @Valid @RequestBody JobRequestDTO jobRequestDTO) {
    	
        JobResponseDTO response = jobService.createJob(jobRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);          
    }

    //=================== GET /api/jobs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJobById( @PathVariable Long id) {

        JobResponseDTO response = jobService.getJobById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //==================== GET /api/jobs
    @GetMapping
    public ResponseEntity<List<JobResponseDTO>> getAllJobs() {

    	List<JobResponseDTO> response = jobService.getAllJobs();
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //===================== GET /api/jobs/company/{companyId}
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponseDTO>> getJobsByCompany( @PathVariable Long companyId) {

        List<JobResponseDTO> response = jobService.getJobsByCompany(companyId);
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //====================== GET /api/jobs/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobResponseDTO>> getJobsByStatus( @PathVariable JobStatus status) {

        List<JobResponseDTO> response = jobService.getJobsByStatus(status);
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //======================= GET /api/jobs/type/{jobType}
    @GetMapping("/type/{jobType}")
    public ResponseEntity<List<JobResponseDTO>> getJobsByType(@PathVariable JobType jobType) {

        List<JobResponseDTO> response = jobService.getJobsByType(jobType);
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //========================= GET /api/jobs/location/{location}
    @GetMapping("/location/{location}")
    public ResponseEntity<List<JobResponseDTO>> getJobsByLocation(@PathVariable String location) {

        List<JobResponseDTO> response = jobService.getJobsByLocation(location);
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //======================= GET /api/jobs/company/{companyId}/status/{status}
    @GetMapping("/company/{companyId}/status/{status}")
    public ResponseEntity<List<JobResponseDTO>> getJobsByCompanyAndStatus(@PathVariable Long companyId,@PathVariable JobStatus status) {

        List<JobResponseDTO> response = jobService.getJobsByCompanyAndStatus(companyId, status);
        return new ResponseEntity<>(response, HttpStatus.OK);              
    }

    //====================== GET /api/jobs/experience?max=3
    @GetMapping("/experience")
    public ResponseEntity<List<JobResponseDTO>> getJobsByMaxExperience(@RequestParam int max) {

        List<JobResponseDTO> response = jobService.getJobsByMaxExperience(max);
        return new ResponseEntity<>(response, HttpStatus.OK);              
    }

    //======================= PUT /api/jobs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDTO> updateJob(@PathVariable Long id,@Valid @RequestBody JobRequestDTO jobRequestDTO) {

        JobResponseDTO response = jobService.updateJob(id, jobRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);              
    }

    //=========================== PATCH /api/jobs/{id}/status?status=CLOSED
    @PatchMapping("/{id}/status")
    public ResponseEntity<JobResponseDTO> updateJobStatus(@PathVariable Long id,@RequestParam JobStatus status) {

        JobResponseDTO response = jobService.updateJobStatus(id, status);
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //=========================== DELETE /api/jobs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {

        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);               
    }
}
