package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeRequestDTO {
	
    @NotBlank(message = "fileName is required")
    private String fileName;               

    @NotBlank(message = "fileType is required")
    private String fileType;               
	    
    @NotBlank(message = "fileUrl is required")
    private String fileUrl;                     

    private boolean isDefault = false;

    @NotNull(message = "candidateId is required")
    private Long candidateId;
	

}
