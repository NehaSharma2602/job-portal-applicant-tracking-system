package com.Neha.job_portal_applicant_tracking_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Neha.job_portal_applicant_tracking_system.entity.Company;

/**
 * Repository interface for Company database operations.
 * Extends JpaRepository to get built-in CRUD methods like save, findById, findAll, delete and existsById.
 * Custom query methods follow Spring Data JPA naming conventions.
 */

public interface CompanyRepository extends JpaRepository<Company, Long> {

	/**
     * Checks whether a company with the given name already exists.
     * Used during company creation and update to prevent duplicates.
     *
     * @param companyName the company name to check
     * @return true if company name already exists, false otherwise
     */
	boolean existsByCompanyName(String companyName);
	
	/**
     * Finds a company by its name.
     * Used when fetching company details by name.
     *
     * @param companyName the company name to search for
     * @return Optional containing the company if found, empty otherwise
     */
	Optional<Company> findByCompanyName(String companyName);
	
	/**
     * Retrieves all active companies.
     * Used to list only companies available for job postings.
     *
     * @return List of companies where active = true
     */
	List<Company> findByActiveTrue();
	
	/**
     * Retrieves all inactive companies.
     * Used by admin to view deactivated companies.
     *
     * @return List of companies where active = false
     */
	List<Company> findByActiveFalse();
	
	/**
     * Retrieves all companies in a specific industry.
     * Used to filter companies by industry type.
     *
     * @param industry the industry name e.g. "IT", "Finance"
     * @return List of companies in that industry
     */
	List<Company> findByIndustry(String industry);
	
	/**
     * Retrieves all companies in a specific location.
     * Used to filter companies by location.
     *
     * @param location the location name e.g. "Mumbai", "Delhi"
     * @return List of companies in that location
     */
	List<Company> findByLocation(String location);
	
	
}
