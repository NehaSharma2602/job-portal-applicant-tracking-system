package com.Neha.job_portal_applicant_tracking_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Neha.job_portal_applicant_tracking_system.dto.ApiResponse;
import com.Neha.job_portal_applicant_tracking_system.dto.CompanyRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.CompanyResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.service.CompanyService;

import jakarta.validation.Valid;

/**
 * REST Controller for managing Company operations.
 * Exposes endpoints for creating, retrieving, updating, deactivating and deleting companies.
 * Base URL — /api/companies
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	/** Service layer dependency for company business logic */
    private final CompanyService companyService;

    /**
     * Constructor injection of CompanyService.
     * Preferred over field injection for better testability.
     *
     * @param companyService the service handling company business logic
     */
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Creates a new company in the system.
     * Validates request body fields before processing.
     *
     * @param companyRequestDTO the company details from request body
     * @return ApiResponse containing the saved company details with HTTP status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> createCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO) {

        CompanyResponseDTO response = companyService.createCompany(companyRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Company created successfully", response), HttpStatus.CREATED);
    }

    /**
     * Retrieves a company by its ID along with its job list.
     *
     * @param id the ID of the company to retrieve
     * @return ApiResponse containing the company details with HTTP status 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> getCompanyById(@PathVariable Long id) {

        CompanyResponseDTO response = companyService.getCompanyById(id);
        return new ResponseEntity<>(ApiResponse.success("Company fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves a company by its name.
     *
     * @param companyName the name of the company to retrieve
     * @return ApiResponse containing the company details with HTTP status 200 OK
     */
    @GetMapping("/name/{companyName}")
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> getCompanyByName(@PathVariable String companyName) {

        CompanyResponseDTO response = companyService.getCompanyByName(companyName);
        return new ResponseEntity<>(ApiResponse.success("Company fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all companies in the system.
     *
     * @return ApiResponse containing list of all companies with HTTP status 200 OK
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getAllCompanies() {

        List<CompanyResponseDTO> response = companyService.getAllCompanies();
        return new ResponseEntity<>(ApiResponse.success("All companies fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all active companies in the system.
     *
     * @return ApiResponse containing list of active companies with HTTP status 200 OK
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getAllActiveCompanies() {

        List<CompanyResponseDTO> response = companyService.getAllActiveCompanies();
        return new ResponseEntity<>(ApiResponse.success("Active companies fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all inactive companies in the system.
     * Used by admin to view deactivated companies.
     *
     * @return ApiResponse containing list of inactive companies with HTTP status 200 OK
     */
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getAllInactiveCompanies() {

        List<CompanyResponseDTO> response = companyService.getAllInactiveCompanies();
        return new ResponseEntity<>(ApiResponse.success("Inactive companies fetched successfully", response), HttpStatus.OK);
    }
    
    /**
     * Retrieves all companies filtered by industry.
     *
     * @param industry the industry name e.g. "IT", "Finance"
     * @return ApiResponse containing list of companies in that industry with HTTP status 200 OK
     */
    @GetMapping("/industry/{industry}")
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getCompaniesByIndustry(@PathVariable String industry) {

        List<CompanyResponseDTO> response = companyService.getCompaniesByIndustry(industry);
        return new ResponseEntity<>(ApiResponse.success("Companies fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Retrieves all companies filtered by location.
     *
     * @param location the location name e.g. "Mumbai", "Delhi"
     * @return ApiResponse containing list of companies in that location with HTTP status 200 OK
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getCompaniesByLocation(@PathVariable String location) {

        List<CompanyResponseDTO> response = companyService.getCompaniesByLocation(location);
        return new ResponseEntity<>(ApiResponse.success("Companies fetched successfully", response), HttpStatus.OK);
    }

    /**
     * Updates an existing company's details by its ID.
     * Validates request body fields before processing.
     *
     * @param id                the ID of the company to update
     * @param companyRequestDTO the updated company details from request body
     * @return ApiResponse containing the updated company details with HTTP status 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> updateCompany(@PathVariable Long id, 
    		@Valid @RequestBody CompanyRequestDTO companyRequestDTO) {

        CompanyResponseDTO response = companyService.updateCompany(id, companyRequestDTO);
        return new ResponseEntity<>(ApiResponse.success("Company updated successfully", response), HttpStatus.OK);
    }

    /**
     * Deactivates a company by setting active = false.
     * This is a soft delete — company data is preserved in the database.
     * All jobs under this company will also become inaccessible.
     *
     * @param id the ID of the company to deactivate
     * @return ApiResponse with success message with HTTP status 200 OK
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateCompany( @PathVariable Long id) {

        companyService.deactivateCompany(id);
        return new ResponseEntity<>(ApiResponse.success("Company deactivated successfully"), HttpStatus.OK);
    }

    /**
     * Permanently deletes a company from the system.
     * This is a hard delete — data cannot be recovered.
     * Also deletes all jobs under this company due to CascadeType.ALL.
     *
     * @param id the ID of the company to delete
     * @return ApiResponse with success message with HTTP status 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Long id) {

        companyService.deleteCompany(id);
        return new ResponseEntity<>(ApiResponse.success("Company deleted successfully"), HttpStatus.NO_CONTENT);
    }
}