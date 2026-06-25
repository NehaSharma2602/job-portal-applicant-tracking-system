package com.Neha.job_portal_applicant_tracking_system.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
@Entity
@Table(name = "job")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_id")
	private Long id;
	
	@NotBlank(message = "title is required")
	@Size(min = 2, max = 100, message = "title must be between 2 and 100 characters")
	@Column(nullable = false)
	private String title;
	
	@NotBlank(message = "description is required")
    @Size(max = 1000, message = "description cannot exceed 1000 characters")
    @Column(nullable = false, length = 1000)
    private String description;

    @NotBlank(message = "requiredSkills is required")
    @Column(nullable = false, name = "required_skills")
    private String requiredSkills;
	
	@Min(value = 0, message = "experienceRequired cannot be negative ")
	@Column(nullable = false, name = "experience_required")
	private int experienceRequired;
	
	@NotNull(message = "salary is reqired")
	@DecimalMin(value = "0.0", inclusive = false, message = "salary must be greater than 0")
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal salary;
	
	@NotBlank(message = "location is required")
	@Column(nullable = false)
	private String location;
	
	@NotNull(message = "jobType is required")
    @Enumerated(EnumType.STRING)               // Stores "FULL_TIME" not 0/1 in DB
    @Column(nullable = false, name = "job_type")
    private JobType jobType;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private JobStatus status = JobStatus.OPEN;
	
	@CreationTimestamp
    @Column(updatable = false, name = "created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
    @Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	 @NotNull(message = "company is required")
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "company_id", nullable = false)
	private Company company;
}
