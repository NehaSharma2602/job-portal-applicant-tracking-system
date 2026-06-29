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

    // ========================POST /api/companies======================
    @PostMapping
    public ResponseEntity<CompanyResponseDTO> createCompany(
            @Valid @RequestBody CompanyRequestDTO companyRequestDTO) {

        CompanyResponseDTO response = companyService.createCompany(companyRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);          
    }

    //================================ GET /api/companies/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> getCompanyById(
            @PathVariable Long id) {

        CompanyResponseDTO response = companyService.getCompanyById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //============================= GET /api/companies/name/{companyName}
    @GetMapping("/name/{companyName}")
    public ResponseEntity<CompanyResponseDTO> getCompanyByName(
            @PathVariable String companyName) {

        CompanyResponseDTO response = companyService.getCompanyByName(companyName);
        return new ResponseEntity<>(response, HttpStatus.OK);              
    }

    //============================ GET /api/companies
    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getAllCompanies() {

        List<CompanyResponseDTO> response = companyService.getAllCompanies();
        return new ResponseEntity<>(response, HttpStatus.OK);              
    }

    //================================ GET /api/companies/active
    @GetMapping("/active")
    public ResponseEntity<List<CompanyResponseDTO>> getAllActiveCompanies() {

        List<CompanyResponseDTO> response = companyService.getAllActiveCompanies();
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //=================================== GET /api/companies/inactive
    @GetMapping("/inactive")
    public ResponseEntity<List<CompanyResponseDTO>> getAllInactiveCompanies() {

        List<CompanyResponseDTO> response = companyService.getAllInactiveCompanies();
        return new ResponseEntity<>(response, HttpStatus.OK);               
    }

    //===================================== GET /api/companies/industry/{industry}
    @GetMapping("/industry/{industry}")
    public ResponseEntity<List<CompanyResponseDTO>> getCompaniesByIndustry(
            @PathVariable String industry) {

        List<CompanyResponseDTO> response = companyService.getCompaniesByIndustry(industry);
        return new ResponseEntity<>(response, HttpStatus.OK);              
    }

    //================================= GET /api/companies/location/{location}
    @GetMapping("/location/{location}")
    public ResponseEntity<List<CompanyResponseDTO>> getCompaniesByLocation(
            @PathVariable String location) {

        List<CompanyResponseDTO> response = companyService.getCompaniesByLocation(location);
        return new ResponseEntity<>(response, HttpStatus.OK);              
    }

    //================================ PUT /api/companies/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequestDTO companyRequestDTO) {

        CompanyResponseDTO response = companyService.updateCompany(id, companyRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);              
    }

    //========================== PATCH /api/companies/{id}/deactivate
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateCompany(
            @PathVariable Long id) {

        companyService.deactivateCompany(id);
        return new ResponseEntity<>(
                "Company deactivated successfully with id: " + id, HttpStatus.OK);  
    }


    //========================= DELETE /api/companies/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long id) {

        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);                 
    }
}
