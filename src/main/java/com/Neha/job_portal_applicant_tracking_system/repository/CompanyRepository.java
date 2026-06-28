package com.Neha.job_portal_applicant_tracking_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Neha.job_portal_applicant_tracking_system.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	boolean existsByCompanyName(String companyName);
	
	Optional<Company> findByCompanyName(String companyName);
	
	List<Company> findByActiveTrue();
	
	List<Company> findByActiveFalse();
	
	List<Company> findByIndustry(String industry);
	
	List<Company> findByLocation(String location);
	
	
}
