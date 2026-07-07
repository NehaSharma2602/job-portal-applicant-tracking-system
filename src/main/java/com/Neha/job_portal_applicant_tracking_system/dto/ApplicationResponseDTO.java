package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDateTime;

import com.Neha.job_portal_applicant_tracking_system.entity.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {

    private Long id;
    private LocalDateTime applicationDate;
    private ApplicationStatus status;
    private String coverLetter;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // From Candidate — safe fields only
    private Long candidateId;
    private String candidateFirstName;
    private String candidateLastName;
    private String candidateEmail;

    // From Job — key fields only
    private Long jobId;
    private String jobTitle;
    private String jobLocation;

    // From Company — via Job
    private String companyName;

    // From Resume — key fields only
    private Long resumeId;
    private String resumeFileName;
}
