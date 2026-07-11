package com.Neha.job_portal_applicant_tracking_system.entity;

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
import jakarta.validation.constraints.Future;
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
@Table(name = "interview")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id")
    private Long id;

    @NotNull(message = "scheduledAt is required")
    @Future(message = "scheduledAt must be a future date and time")
    @Column(nullable = false, name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @NotNull(message = "mode is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewMode mode;             

    @Column(name = "location")
    private String location;             


    @Column(name = "meeting_link")
    private String meetingLink;          

    @Size(max = 1000, message = "feedback cannot exceed 1000 characters")
    @Column(length = 1000)
    private String feedback;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewResult result = InterviewResult.PENDING;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //============= RELATIONSHIP — Many Interviews → One Application==============//
    @NotNull(message = "application is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
}