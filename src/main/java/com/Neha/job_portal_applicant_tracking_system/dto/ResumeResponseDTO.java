package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.Neha.job_portal_applicant_tracking_system.entity.Candidate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ResumeResponseDTO {

	private Long id;
	
    private String fileName;               

    private String fileType;               
    
    private String fileUrl;                

    private LocalDateTime uploadedAt;      

    private boolean isDefault;

    //===================== From Candidate — only safe fields=============================//
    private Long candidateId;
    private String candidateFirstName;
    private String candidateLastName;
    private String candidateEmail;


}
