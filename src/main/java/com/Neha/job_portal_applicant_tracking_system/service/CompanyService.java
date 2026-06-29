package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.CompanyRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.CompanyResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.repository.CompanyRepository;

public interface CompanyService {

	CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO);
	
	CompanyResponseDTO getCompanyById(Long id);
	
	CompanyResponseDTO getCompanyByName(String companyName);
	
	List<CompanyResponseDTO> getAllCompanies(); 
	
	List<CompanyResponseDTO> getAllActiveCompanies();
	
	List<CompanyResponseDTO> getAllInactiveCompanies();
	
	List<CompanyResponseDTO> getCompaniesByIndusty(String industry);
	
	List<CompanyResponseDTO> getCompaniesByLocation(String Location);
	
	CompanyResponseDTO updateCompany(Long id, CompanyRequestDTO companyRequestDTO);
	
	void deactivateCompany(Long id);
	
	void deleteCompany(Long id);

	
}
