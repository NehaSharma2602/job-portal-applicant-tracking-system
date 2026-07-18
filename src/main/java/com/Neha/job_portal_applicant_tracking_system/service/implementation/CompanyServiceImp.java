package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.CompanyRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.CompanyResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.JobResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Company;
import com.Neha.job_portal_applicant_tracking_system.entity.Job;
import com.Neha.job_portal_applicant_tracking_system.exception.CompanyAlreadyExistsException;
import com.Neha.job_portal_applicant_tracking_system.exception.CompanyInactiveException;
import com.Neha.job_portal_applicant_tracking_system.exception.CompanyNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.JobNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.repository.CompanyRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.JobRepository;
import com.Neha.job_portal_applicant_tracking_system.service.CompanyService;

import jakarta.transaction.Transactional;

/**
 * Implementation of {@link CompanyService} interface.
 * Contains all business logic for Company operations.
 * Interacts with {@link CompanyRepository} for database operations.
 *
 * <p>All methods follow this pattern:
 * <ol>
 *   <li>Validate input and check business rules</li>
 *   <li>Perform database operation</li>
 *   <li>Convert entity to DTO and return</li>
 * </ol>
 */
@Service
public class CompanyServiceImp implements CompanyService {

	/** Repository for Company database operations */
	private final CompanyRepository companyRepo;
	
	/** Repository for Job database Operations */
	private final JobRepository jobRepo;

	/**
     * Constructor injection of CompanyRepository.
     * Preferred over field injection for better testability.
     *
     * @param companyRepo repository for company DB operations
     * * @param jobRepo repository for job DB operations
     */
	public CompanyServiceImp(CompanyRepository companyRepo, JobRepository jobRepo) {
		super();
		this.companyRepo = companyRepo;
		this.jobRepo = jobRepo;
	}
	
	//===================mapping Company Entity → CompanyResponseDTO ==============================================//
	  /**
     * Converts a {@link Company} entity to a {@link CompanyResponseDTO}.
     * Also converts the list of Job entities to JobResponseDTOs to avoid circular reference in JSON response.
     *
     * @param company the Company entity to convert
     * @return CompanyResponseDTO containing company and job details
     */
	private CompanyResponseDTO mapToResponseDTO(Company company) {
		CompanyResponseDTO dto = new CompanyResponseDTO();
		dto.setId(company.getId());
		dto.setCompanyName(company.getCompanyName());
		dto.setDescription(company.getDescription());
		dto.setWebsite(company.getWebsite());
		dto.setIndustry(company.getIndustry());
		dto.setLocation(company.getLocation());
		dto.setActive(company.isActive());
		dto.setCreatedAt(company.getCreatedAt());
		dto.setUpdatedAt(company.getUpdatedAt());
		
		// get all the jobs from company
		List<Job> jobList = company.getJobs();
		
		// converts jobs only if the list is not null
		if( jobList != null) {
			List<JobResponseDTO> jobDTOs = new ArrayList<>();
			for(Job job: jobList) {
				JobResponseDTO jobDTO = mapJobToResponseDTO(job);
				jobDTOs.add(jobDTO);
			}
			dto.setJobs(jobDTOs);
		}
		return dto;
		
	}

    //======================================= mapping job entity to job reponseDTO =============================================//
	/**
     * Converts a {@link Job} entity to a {@link JobResponseDTO}.
     * Only companyName is included as a String — not the full Company object — to prevent circular JSON nesting.
     *
     * @param job the Job entity to convert
     * @return JobResponseDTO containing job details
     */
    private JobResponseDTO mapJobToResponseDTO(Job job) {

        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setRequiredSkills(job.getRequiredSkills());
        dto.setExperienceRequired(job.getExperienceRequired());
        dto.setSalary(job.getSalary());
        dto.setLocation(job.getLocation());
        dto.setJobType(job.getJobType());
        dto.setStatus(job.getStatus());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());
        dto.setCompanyName(job.getCompany().getCompanyName());

        return dto;
    }
    
    //================================================ create company ============================================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>Company name must not already exist in the system</li>
     * </ul>
     */
    @Override
    @Transactional
    public CompanyResponseDTO createCompany(CompanyRequestDTO dto) {
    	boolean exists = companyRepo.existsByCompanyName(dto.getCompanyName());
    	
    	if(exists) {
    		throw new CompanyAlreadyExistsException("Company already exits with name: " + dto.getCompanyName());
    	}
    	Company com = new Company();
    	com.setCompanyName(dto.getCompanyName());
    	com.setDescription(dto.getDescription());
    	com.setIndustry(dto.getIndustry());
    	com.setLocation(dto.getLocation());
    	com.setWebsite(dto.getWebsite());
    	com.setActive(true);
    	
    	// save to db and convert dto then return
    	Company savedCompany = companyRepo.save(com);
    	
    	return mapToResponseDTO(savedCompany);
    }
    
    //================================================= get company by id ===========================================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>Company must exist with the given ID</li>
     * </ul>
     */
    @Override
    public CompanyResponseDTO getCompanyById(Long id) {
    	Company com = companyRepo.findById(id).orElseThrow(
    			() -> new CompanyNotFoundException("Company not found with id: " + id));
    	
    	return mapToResponseDTO(com);
    }
    
    //================================================ get company by name ==========================================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>Company must exist with the given name</li>
     * </ul>
     */
    @Override
    public CompanyResponseDTO getCompanyByName(String companyName) {
    	Company com = companyRepo.findByCompanyName(companyName).orElseThrow(()-> new CompanyNotFoundException("Company not found with name: " + companyName));
    		return mapToResponseDTO(com);
    }
    
    //============================================= get all companies =================================================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>At least one company must exist in the system</li>
     * </ul>
     */
    @Override
    public List<CompanyResponseDTO> getAllCompanies() {
    	List<Company> com = companyRepo.findAll();
    	
    	if(com.isEmpty()) {
    		throw new CompanyNotFoundException("No companies found in the system");
    	}
    	List<CompanyResponseDTO> response = new ArrayList<>();
    	for(Company company : com) {
    		CompanyResponseDTO dto = mapToResponseDTO(company);
    		response.add(dto);
    	}
    	return response;
    	
    }
    
    //========================================== get all active companies ======================================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>At least one active company must exist</li>
     * </ul>
     */
    @Override
    public List<CompanyResponseDTO> getAllActiveCompanies() {

        List<Company> companies = companyRepo.findByActiveTrue();

        if (companies.isEmpty()) {
            throw new CompanyNotFoundException("No active companies found");
        }

        List<CompanyResponseDTO> responseDTOs = new ArrayList<>();
        for (Company company : companies) {
            CompanyResponseDTO dto = mapToResponseDTO(company);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }
    
    //======================================= get all inactive companies =============================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>At least one inactive company must exist</li>
     * </ul>
     */
    @Override
    public List<CompanyResponseDTO> getAllInactiveCompanies() {

        List<Company> companies = companyRepo.findByActiveFalse();
        if (companies.isEmpty()) {
            throw new CompanyNotFoundException("No inactive companies found");
        }
        List<CompanyResponseDTO> responseDTOs = new ArrayList<>();
        for (Company company : companies) {
            CompanyResponseDTO dto = mapToResponseDTO(company);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    //================================================ get companies by industries =================================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>At least one company must exist in that industry</li>
     * </ul>
     */
    @Override
    public List<CompanyResponseDTO> getCompaniesByIndustry(String industry) {

        List<Company> companies = companyRepo.findByIndustry(industry);

        if (companies.isEmpty()) {
            throw new CompanyNotFoundException("No companies found in industry: " + industry);
        }

        List<CompanyResponseDTO> responseDTOs = new ArrayList<>();
        for (Company company : companies) {
            CompanyResponseDTO dto = mapToResponseDTO(company);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    //===================================== get companies by location ==================================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>At least one company must exist in that location</li>
     * </ul>
     */
    @Override
    public List<CompanyResponseDTO> getCompaniesByLocation(String location) {


        List<Company> companies = companyRepo.findByLocation(location);

        if (companies.isEmpty()) {
            throw new CompanyNotFoundException("No companies found in location: " + location);
        }

        List<CompanyResponseDTO> responseDTOs = new ArrayList<>();
        for (Company company : companies) {
            CompanyResponseDTO dto = mapToResponseDTO(company);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    //======================================================= update company =============================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>Company must exist with the given ID</li>
     *   <li>Company must be active</li>
     *   <li>New company name must not be taken by another company</li>
     * </ul>
     */
    @Override
    @Transactional
    public CompanyResponseDTO updateCompany(Long id, CompanyRequestDTO dto) {

        Company company = companyRepo.findById(id)
            .orElseThrow(() -> new CompanyNotFoundException(
                "Company not found with id: " + id));
        if (!company.isActive()) {
            throw new CompanyInactiveException(
                "Cannot update inactive company with id: " + id);
        }
        boolean isNameChanging = !company.getCompanyName().equals(dto.getCompanyName());
        boolean isNewNameTaken = companyRepo.existsByCompanyName(dto.getCompanyName());

        if (isNameChanging && isNewNameTaken) {
            throw new CompanyAlreadyExistsException(
                "Company name already taken: " + dto.getCompanyName());
        }

        company.setCompanyName(dto.getCompanyName());
        company.setDescription(dto.getDescription());
        company.setWebsite(dto.getWebsite());
        company.setIndustry(dto.getIndustry());
        company.setLocation(dto.getLocation());
        company.setUpdatedAt(LocalDateTime.now());

        Company updatedCompany = companyRepo.save(company);
        return mapToResponseDTO(updatedCompany);
    }

    //============================================= deactive company ===============================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>Company must exist with the given ID</li>
     *   <li>Company must not already be inactive</li>
     * </ul>
     */
    @Override
    @Transactional
    public void deactivateCompany(Long id) {

        Company company = companyRepo.findById(id)
            .orElseThrow(() -> new CompanyNotFoundException(
                "Company not found with id: " + id));

        if (!company.isActive()) {
            throw new CompanyInactiveException(
                "Company is already inactive with id: " + id);
        }
        company.setActive(false);
        company.setUpdatedAt(LocalDateTime.now());
        companyRepo.save(company);
    }

    //============================================ delete company =================================================//
    /**
     * {@inheritDoc}
     *
     * Business rules checked:
     * <ul>
     *   <li>Company must exist with the given ID</li>
     * </ul>
     * Note: Deleting a company also deletes all its jobs due to CascadeType.ALL on the jobs relationship.
     */
    @Override
    @Transactional
    public void deleteCompany(Long id) {

        Company company = companyRepo.findById(id)
            .orElseThrow(() -> new CompanyNotFoundException(
                "Company not found with id: " + id));

        companyRepo.delete(company);
    }

}
