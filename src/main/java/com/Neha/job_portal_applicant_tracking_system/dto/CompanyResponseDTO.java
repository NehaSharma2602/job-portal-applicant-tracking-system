package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDTO {

    private Long id;
    private String companyName;
    private String description;
    private String website;
    private String industry;
    private String location;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<JobResponseDTO> jobs;     // Shows all jobs under this company

}
