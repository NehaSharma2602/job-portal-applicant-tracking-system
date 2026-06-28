package com.Neha.job_portal_applicant_tracking_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.Neha.job_portal_applicant_tracking_system.entity.JobStatus;
import com.Neha.job_portal_applicant_tracking_system.entity.JobType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDTO {

	private Long id;
    private String title;
    private String description;
    private String requiredSkills;
    private int experienceRequired;
    private BigDecimal salary;
    private String location;
    private JobType jobType;
    private JobStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String companyName;     
}
