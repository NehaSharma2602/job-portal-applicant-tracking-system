package com.Neha.job_portal_applicant_tracking_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Neha.job_portal_applicant_tracking_system.entity.User;

/**
 * Repository interface for User database operations.
 * Extends JpaRepository to get built-in CRUD methods
 * like save, findById, findAll, delete and existsById.
 * Custom query methods follow Spring Data JPA naming conventions.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * Used during login and profile lookup.
     *
     * @param email the email address to search for
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether a user with the given email already exists.
     * Used during registration to prevent duplicate accounts.
     *
     * @param email the email address to check
     * @return true if email is already registered, false otherwise
     */
    boolean existsByEmail(String email);
}
