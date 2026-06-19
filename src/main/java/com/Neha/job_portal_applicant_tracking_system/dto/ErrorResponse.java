package com.Neha.job_portal_applicant_tracking_system.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

	private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
