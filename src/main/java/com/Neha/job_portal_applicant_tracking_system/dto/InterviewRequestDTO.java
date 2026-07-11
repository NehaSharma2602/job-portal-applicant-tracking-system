package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDateTime;

import com.Neha.job_portal_applicant_tracking_system.entity.InterviewMode;

import jakarta.validation.constraints.Future;
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
public class InterviewRequestDTO {

    @NotNull(message = "applicationId is required")
    private Long applicationId;

    @NotNull(message = "scheduledAt is required")
    @Future(message = "scheduledAt must be a future date and time")
    private LocalDateTime scheduledAt;

    @NotNull(message = "mode is required")
    private InterviewMode mode;

    // Optional — only needed for OFFLINE or HYBRID
    private String location;

    // Optional — only needed for ONLINE or HYBRID
    private String meetingLink;

    // Optional — filled by recruiter after interview
    @Size(max = 1000, message = "feedback cannot exceed 1000 characters")
    private String feedback;
}