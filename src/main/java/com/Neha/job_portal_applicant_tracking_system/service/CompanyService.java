package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.CompanyRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.CompanyResponseDTO;

/**
 * Service interface for managing Company operations.
 * Handles business logic for creating, retrieving,
 * updating, and deleting companies.
 */
public interface CompanyService {

    /**
     * Creates a new company in the system.
     *
     * @param companyRequestDTO the company details from the request body
     * @return CompanyResponseDTO the saved company details
     * @throws CompanyAlreadyExistsException if a company with the same name already exists
     */
    CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO);

    /**
     * Retrieves a company by its ID.
     *
     * @param id the ID of the company
     * @return CompanyResponseDTO the company details including its jobs
     * @throws CompanyNotFoundException if no company is found with the given ID
     */
    CompanyResponseDTO getCompanyById(Long id);

    /**
     * Retrieves a company by its name.
     *
     * @param companyName the name of the company
     * @return CompanyResponseDTO the company details
     * @throws CompanyNotFoundException if no company is found with the given name
     */
    CompanyResponseDTO getCompanyByName(String companyName);

    /**
     * Retrieves all companies in the system.
     *
     * @return List of CompanyResponseDTO
     * @throws CompanyNotFoundException if no companies exist
     */
    List<CompanyResponseDTO> getAllCompanies();

    /**
     * Retrieves all active companies.
     *
     * @return List of CompanyResponseDTO where active = true
     * @throws CompanyNotFoundException if no active companies exist
     */
    List<CompanyResponseDTO> getAllActiveCompanies();

    /**
     * Retrieves all inactive companies.
     *
     * @return List of CompanyResponseDTO where active = false
     * @throws CompanyNotFoundException if no inactive companies exist
     */
    List<CompanyResponseDTO> getAllInactiveCompanies();

    /**
     * Retrieves companies filtered by industry.
     *
     * @param industry the industry name e.g. "IT", "Finance"
     * @return List of CompanyResponseDTO in that industry
     * @throws CompanyNotFoundException if no companies exist in that industry
     */
    List<CompanyResponseDTO> getCompaniesByIndustry(String industry);

    /**
     * Retrieves companies filtered by location.
     *
     * @param location the location name e.g. "Mumbai", "Delhi"
     * @return List of CompanyResponseDTO in that location
     * @throws CompanyNotFoundException if no companies exist in that location
     */
    List<CompanyResponseDTO> getCompaniesByLocation(String location);

    /**
     * Updates an existing company's details.
     *
     * @param id                the ID of the company to update
     * @param companyRequestDTO the updated company details
     * @return CompanyResponseDTO the updated company details
     * @throws CompanyNotFoundException      if no company is found with the given ID
     * @throws CompanyInactiveException      if the company is deactivated
     * @throws CompanyAlreadyExistsException if the new name is already taken
     */
    CompanyResponseDTO updateCompany(Long id, CompanyRequestDTO companyRequestDTO);

    /**
     * Deactivates a company — sets active = false.
     * The company still exists in the database (soft delete).
     *
     * @param id the ID of the company to deactivate
     * @throws CompanyNotFoundException if no company is found with the given ID
     * @throws CompanyInactiveException if the company is already inactive
     */
    void deactivateCompany(Long id);

    /**
     * Permanently deletes a company from the system.
     * Also deletes all jobs under this company due to CascadeType.ALL.
     *
     * @param id the ID of the company to delete
     * @throws CompanyNotFoundException if no company is found with the given ID
     */
    void deleteCompany(Long id);
}