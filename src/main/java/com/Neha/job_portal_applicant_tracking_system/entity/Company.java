package com.Neha.job_portal_applicant_tracking_system.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long id;
	
	@NotBlank(message = "companyName is required")
	@Size(min = 2,  max = 100, message = "companyName must be between 2 and 100 characters")
	@Column(nullable = false, unique = true, name = "company_name")
	private String companyName;
	
	@Size(max = 500, message = "description cannot exceed 500 characters")
	@Column(length = 500)
	private String description;
	
	@Pattern(
			regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/.*)?$",
	        message = "Invalid website URL format"
			)
	@Column(unique = true)
	private String website;
	
	@NotBlank(message = "location is required")
	@Column(nullable = false)
	private String industry;
	
	@NotBlank(message = "location is required")
	@Column(nullable = false)
	private String location;
	
	@Column(nullable = false, name = "is_active")
	private boolean active = true;
	
	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	// One Company → Many Jobs
    // mappedBy = "company" → refers to the field name in Job entity
    // cascade = ALL → any operation on Company cascades to its Jobs
    // orphanRemoval = true → if a Job is removed from the list, delete it from DB
    // fetch = LAZY → Jobs are only loaded when explicitly accessed (performance)
	@OneToMany(
			mappedBy = "company",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private List<Job> jobs;
	
}
