package com.Neha.job_portal_applicant_tracking_system.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
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
public class User {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "user_id")
	private Long id;
	
	@NotBlank(message = "firstName is required")
	private String firstName;
	
	@NotBlank(message = "lastName is required")
	private String lastName;
	
	@NotBlank(message = "email is required")
	@Email(message = "invalid email format")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "password is required")
	private String password;
	
	@NotBlank(message = "phoneNumber is required")
	private String phoneNumber;
	
	@Column(nullable = false, name = "is_active")
	private boolean active = true;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false) //@JoinColumn => Specifies the foreign key column that links two tables together. name = "role_id" => The actual column name in the database table will be role_id. nullable = false => The foreign key column cannot be NULL — meaning every record must have an associated role.
	private Role role;
	
}
