package com.Neha.job_portal_applicant_tracking_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequestDTO {

    @NotNull(message = "candidateId is required")
    private Long candidateId;

    @NotNull(message = "jobId is required")
    private Long jobId;

    @NotNull(message = "resumeId is required")
    private Long resumeId;

    // Optional — candidate may or may not write a cover letter
    @Size(max = 1000, message = "coverLetter cannot exceed 1000 characters")
    private String coverLetter;
}