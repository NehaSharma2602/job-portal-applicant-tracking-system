package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {
	
	private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean active;
    private String roleName;
    private LocalDateTime createdAt;

}
