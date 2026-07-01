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

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> createCompany(
            @Valid @RequestBody CompanyRequestDTO companyRequestDTO) {

        CompanyResponseDTO response = companyService.createCompany(companyRequestDTO);
        return new ResponseEntity<>(
            ApiResponse.success("Company created successfully", response),
            HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> getCompanyById(
            @PathVariable Long id) {

        CompanyResponseDTO response = companyService.getCompanyById(id);
        return new ResponseEntity<>(
            ApiResponse.success("Company fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/name/{companyName}")
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> getCompanyByName(
            @PathVariable String companyName) {

        CompanyResponseDTO response = companyService.getCompanyByName(companyName);
        return new ResponseEntity<>(
            ApiResponse.success("Company fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getAllCompanies() {

        List<CompanyResponseDTO> response = companyService.getAllCompanies();
        return new ResponseEntity<>(
            ApiResponse.success("All companies fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getAllActiveCompanies() {

        List<CompanyResponseDTO> response = companyService.getAllActiveCompanies();
        return new ResponseEntity<>(
            ApiResponse.success("Active companies fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getAllInactiveCompanies() {

        List<CompanyResponseDTO> response = companyService.getAllInactiveCompanies();
        return new ResponseEntity<>(
            ApiResponse.success("Inactive companies fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/industry/{industry}")
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getCompaniesByIndustry(
            @PathVariable String industry) {

        List<CompanyResponseDTO> response = companyService.getCompaniesByIndustry(industry);
        return new ResponseEntity<>(
            ApiResponse.success("Companies fetched successfully", response),
            HttpStatus.OK);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getCompaniesByLocation(
            @PathVariable String location) {

        List<CompanyResponseDTO> response = companyService.getCompaniesByLocation(location);
        return new ResponseEntity<>(
            ApiResponse.success("Companies fetched successfully", response),
            HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequestDTO companyRequestDTO) {

        CompanyResponseDTO response = companyService.updateCompany(id, companyRequestDTO);
        return new ResponseEntity<>(
            ApiResponse.success("Company updated successfully", response),
            HttpStatus.OK);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateCompany(
            @PathVariable Long id) {

        companyService.deactivateCompany(id);
        return new ResponseEntity<>(
            ApiResponse.success("Company deactivated successfully"),
            HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(
            @PathVariable Long id) {

        companyService.deleteCompany(id);
        return new ResponseEntity<>(
            ApiResponse.success("Company deleted successfully"),
            HttpStatus.NO_CONTENT);
    }
}