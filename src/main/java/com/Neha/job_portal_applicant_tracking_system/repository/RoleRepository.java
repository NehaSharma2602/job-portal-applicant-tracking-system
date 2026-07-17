package com.Neha.job_portal_applicant_tracking_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Neha.job_portal_applicant_tracking_system.entity.Role;

/**
 * Repository interface for Role database operations.
 * Extends JpaRepository to get built-in CRUD methods like save, findById, findAll, delete and existsById.
 * Custom query methods follow Spring Data JPA naming conventions.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	/**
     * Checks whether a role with the given name already exists.
     * Used during role creation to prevent duplicates.
     *
     * @param role the role name to check e.g. "ADMIN"
     * @return true if role already exists, false otherwise
     */
	boolean existsByRole(String role);
	
	 /**
     * Finds a role by its name.
     * Used when fetching role details by name.
     *
     * @param role the role name to search for e.g. "RECRUITER"
     * @return Optional containing the role if found, empty otherwise
     */
	 Optional<Role> findByRole(String role);

}
