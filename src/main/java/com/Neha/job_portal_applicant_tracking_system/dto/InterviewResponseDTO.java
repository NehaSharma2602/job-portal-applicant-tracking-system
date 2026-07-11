package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDateTime;

import com.Neha.job_portal_applicant_tracking_system.entity.InterviewMode;
import com.Neha.job_portal_applicant_tracking_system.entity.InterviewResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponseDTO {

    private Long id;
    private LocalDateTime scheduledAt;
    private InterviewMode mode;
    private String location;
    private String meetingLink;
    private String feedback;
    private InterviewResult result;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // From Application
    private Long applicationId;
    private String applicationStatus;

    // From Candidate via Application
    private Long candidateId;
    private String candidateFirstName;
    private String candidateLastName;
    private String candidateEmail;

    // From Job via Application
    private Long jobId;
    private String jobTitle;

    // From Company via Job via Application
    private String companyName;
}
