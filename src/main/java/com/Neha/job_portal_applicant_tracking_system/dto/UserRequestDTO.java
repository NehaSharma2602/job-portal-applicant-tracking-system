package com.Neha.job_portal_applicant_tracking_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request DTO for creating or updating a User.
 * Contains all fields sent by the client in the request body.
 * Validation annotations ensure data integrity before reaching the service layer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    /** First name of the user — cannot be blank */
    @NotBlank(message = "firstName is required")
    private String firstName;

    /** Last name of the user — cannot be blank */
    @NotBlank(message = "lastName is required")
    private String lastName;

    /**
     * Email address of the user.
     * Must be in valid email format.
     * Must be unique across the system.
     */
    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;

    /**
     * Password for the user account.
     * Should be encrypted before saving in the database.
     */
    @NotBlank(message = "password is required")
    private String password;

    /** Phone number of the user — cannot be blank */
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    /**
     * ID of the role to assign to this user.
     * e.g. role ID for ADMIN, RECRUITER or CANDIDATE.
     */
    private Long roleId;
}
