package com.Neha.job_portal_applicant_tracking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Neha.job_portal_applicant_tracking_system.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	boolean exitsByRole(String role);

}
