package com.Neha.job_portal_applicant_tracking_system.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "resume")
public class Resume {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
	private Long id;

    @NotBlank(message = "fileName is required")
    @Column(nullable = false, name = "file_name")
    private String fileName;               

    @NotBlank(message = "fileType is required")
    @Column(nullable = false, name = "file_type")
    private String fileType;               
    
    @NotBlank(message = "fileUrl is required")
    @Column(nullable = false, name = "file_url")
    private String fileUrl;                

    @CreationTimestamp
    @Column(updatable = false, name = "uploaded_at")
    private LocalDateTime uploadedAt;      

	//============= Whether this is the candidate's default resume
    //============= Used when applying for jobs — default resume is auto-selected
    @Column(nullable = false, name = "is_default")
    private boolean isDefault = false;

    //============ RELATIONSHIP — Many Resumes → One Candidate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;
}
