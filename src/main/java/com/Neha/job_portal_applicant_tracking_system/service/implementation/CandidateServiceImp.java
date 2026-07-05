package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.CandidateRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.CandidateResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Candidate;
import com.Neha.job_portal_applicant_tracking_system.entity.Gender;
import com.Neha.job_portal_applicant_tracking_system.entity.User;
import com.Neha.job_portal_applicant_tracking_system.exception.CandidateAlreadyExistsException;
import com.Neha.job_portal_applicant_tracking_system.exception.CandidateInactiveException;
import com.Neha.job_portal_applicant_tracking_system.exception.CandidateNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.ResourceNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.UserAlreadyLinkedToACandidateException;
import com.Neha.job_portal_applicant_tracking_system.repository.CandidateRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.UserRepository;
import com.Neha.job_portal_applicant_tracking_system.service.CandidateService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CandidateServiceImp implements CandidateService{

	private final CandidateRepository candidateRepo;
	private final UserRepository userRepo;
	
	//=====mapper candidate entity to candidateResponseDTO
	private CandidateResponseDTO mapToResponseDTO(Candidate candidate) {
		CandidateResponseDTO dto = new CandidateResponseDTO();
        dto.setId(candidate.getId());
        dto.setFirstName(candidate.getFirstName());
        dto.setLastName(candidate.getLastName());
        dto.setEmail(candidate.getEmail());
        dto.setPhoneNumber(candidate.getPhoneNumber());
        dto.setDateOfBirth(candidate.getDateOfBirth());
        dto.setGender(candidate.getGender());
        dto.setCurrentLocation(candidate.getCurrentLocation());
        dto.setExperienceYears(candidate.getExperienceYears());
        dto.setCurrentJobTitle(candidate.getCurrentJobTitle());
        dto.setCurrentSalary(candidate.getCurrentSalary());
        dto.setExpectedSalary(candidate.getExpectedSalary());
        dto.setSkills(candidate.getSkills());
        dto.setActive(candidate.isActive());
        dto.setCreatedAt(candidate.getCreatedAt());
        dto.setUpdatedAt(candidate.getUpdatedAt());
        
        dto.setUserEmail(candidate.getUser().getEmail());
        dto.setUserFirstName(candidate.getUser().getFirstName());
        dto.setUserLastName(candidate.getUser().getLastName());

        return dto;
        
	}
	
	//=====create======//
	@Override
	@Transactional
	public CandidateResponseDTO createCandidate(CandidateRequestDTO dto) {
		
		//1. whether email exists or not
		boolean emailExists = candidateRepo.existsByEmail(dto.getEmail());
		if(emailExists) {
			throw new CandidateAlreadyExistsException("Candidate already exists with email: " + dto.getEmail());
		}
		
		//whether the user has candidate profile not not
		boolean userAlreadyLinked = candidateRepo.existsByUserId(dto.getUserId());
		if(userAlreadyLinked) {
			throw new UserAlreadyLinkedToACandidateException("User already has a candidate profile with userId: " + dto.getUserId());
			
		}
		//if user already has candidate profile then find that profile
		User user = userRepo.findById(dto.getUserId()).orElseThrow(
				() -> new ResourceNotFoundException("User Not found with id: " + dto.getUserId()));
		
		// create candidate and set fields
		Candidate candidate = new Candidate();
		candidate.setFirstName(dto.getFirstName());
		candidate.setLastName(dto.getLastName());
		candidate.setEmail(dto.getEmail());
		candidate.setPhoneNumber(dto.getPhoneNumber());
		candidate.setDateOfBirth(dto.getDateOfBirth());
		candidate.setGender(dto.getGender());
        candidate.setCurrentLocation(dto.getCurrentLocation());
        candidate.setExperienceYears(dto.getExperienceYears());
        candidate.setCurrentJobTitle(dto.getCurrentJobTitle());
        candidate.setCurrentSalary(dto.getCurrentSalary());
        candidate.setExpectedSalary(dto.getExpectedSalary());
        candidate.setSkills(dto.getSkills());
        candidate.setActive(true);
        candidate.setUser(user);
        
        //save and return
        Candidate savedCandidate =  candidateRepo.save(candidate);
        return mapToResponseDTO(savedCandidate);
        
	}
	
	
	//==========by email
	@Override
	 public CandidateResponseDTO getCandidateByEmail(String email) {

	        Candidate candidate = candidateRepo.findByEmail(email)
	            .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with email: " + email));

	        return mapToResponseDTO(candidate);
	 }
	
	
	//===========by id
	@Override
	 public CandidateResponseDTO getCandidateById(Long id) {
		 Candidate candidate = candidateRepo.findById(id).orElseThrow(() -> new CandidateNotFoundException(
				 "Candidate not found with id: " + id));

	     return mapToResponseDTO(candidate);

	 }
	
	
	 //========by user id
	 @Override
	 public CandidateResponseDTO getCandidateByUserId(Long userId) {

		 Candidate candidate = candidateRepo.findByUserId(userId)
				 .orElseThrow(() -> new CandidateNotFoundException("No candidate profile found for userId: " + userId));

	        return mapToResponseDTO(candidate);
	 }
	 
	 
	 //=======all candidates
	 @Override 
	 public List<CandidateResponseDTO> getAllCandidates(){
		  //get all candidates from db
		 List<Candidate> candidates = candidateRepo.findAll();
		 
		 if(candidates.isEmpty()) {
			 throw new CandidateNotFoundException("No Candidate found in the system");
		 }
		 
		 List<CandidateResponseDTO> responseDTOs = new ArrayList<>();
		 for(Candidate candidate : candidates) {
			 CandidateResponseDTO dto = mapToResponseDTO(candidate);
			 responseDTOs.add(dto);
		 }
		 return responseDTOs;
	 }
	 
	 //===== activate candidate
	 @Override
	 public List<CandidateResponseDTO> getAllActiveCandidates() {

		 List<Candidate> candidates = candidateRepo.findByActiveTrue();

	     if (candidates.isEmpty()) {
	    	 throw new CandidateNotFoundException("No active candidates found");
	     }

	     List<CandidateResponseDTO> responseDTOs = new ArrayList<>();
	     for (Candidate candidate : candidates) {
	         CandidateResponseDTO dto = mapToResponseDTO(candidate);
	         responseDTOs.add(dto);
	     }

	     return responseDTOs;
	}
	 
	 
	 //========inactive candidates
	 @Override
	 public List<CandidateResponseDTO> getAllInactiveCandidates() {

		 List<Candidate> candidates = candidateRepo.findByActiveFalse();

	     if (candidates.isEmpty()) {
	    	 throw new CandidateNotFoundException("No inactive candidates found");
	     }

	     List<CandidateResponseDTO> responseDTOs = new ArrayList<>();
	     for (Candidate candidate : candidates) {
	         CandidateResponseDTO dto = mapToResponseDTO(candidate);
	         responseDTOs.add(dto);
	     }

	     return responseDTOs;
	 }
	 
	 
	 //=======by location
	 @Override
	 public List<CandidateResponseDTO> getCandidatesByLocation(String location) {

		 List<Candidate> candidates = candidateRepo.findByCurrentLocation(location);

	     if (candidates.isEmpty()) {
	    	 throw new CandidateNotFoundException("No candidates found in location: " + location);
	     }

	     List<CandidateResponseDTO> responseDTOs = new ArrayList<>();
	     for (Candidate candidate : candidates) {
	         CandidateResponseDTO dto = mapToResponseDTO(candidate);
	         responseDTOs.add(dto);
	     }
	     return responseDTOs;
	 }
	 
	 
	 //=====by gender
	 @Override
	 public List<CandidateResponseDTO> getCandidatesByGender(Gender gender) {

	     List<Candidate> candidates = candidateRepo.findByGender(gender);

	     if (candidates.isEmpty()) {
	         throw new CandidateNotFoundException("No candidates found with gender: " + gender);
	     }

	     List<CandidateResponseDTO> responseDTOs = new ArrayList<>();
	     for (Candidate candidate : candidates) {
	         CandidateResponseDTO dto = mapToResponseDTO(candidate);
	         responseDTOs.add(dto);
	     }

	    return responseDTOs;
	}
	 
	 
	 //===========by maxExperience
	 @Override
	 public List<CandidateResponseDTO> getCandidatesByMaxExperience(int years) {

		 List<Candidate> candidates =
	            candidateRepo.findByExperienceYearsLessThanEqual(years);

	     if (candidates.isEmpty()) {
	    	 throw new CandidateNotFoundException("No candidates found with experience <= " + years + " years");
	     }

	     List<CandidateResponseDTO> responseDTOs = new ArrayList<>();
	     for (Candidate candidate : candidates) {
	         CandidateResponseDTO dto = mapToResponseDTO(candidate);
	         responseDTOs.add(dto);
	     }

	     return responseDTOs;
	 }
	 
	 
	 //by min experience
	 public List<CandidateResponseDTO> getCandidatesByMinExperience(int years) {

		 List<Candidate> candidates = candidateRepo.findByExperienceYearsGreaterThanEqual(years);

	        if (candidates.isEmpty()) {
	            throw new CandidateNotFoundException(
	                "No candidates found with experience >= " + years + " years");
	        }

	        List<CandidateResponseDTO> responseDTOs = new ArrayList<>();
	        for (Candidate candidate : candidates) {
	            CandidateResponseDTO dto = mapToResponseDTO(candidate);
	            responseDTOs.add(dto);
	        }

	        return responseDTOs;
	 }
	 
	 
	 //=======update candidate
	 @Override
	 @Transactional
	 public CandidateResponseDTO updateCandidate(Long id, CandidateRequestDTO dto) {
		 //find the candidate
		 Candidate candidate = candidateRepo.findById(id).orElseThrow(
				 () -> new CandidateNotFoundException("Candidate not found with id: " + id) );
		 
		 // whether candidate is active or not
		 if(!candidate.isActive()) {
			 throw new CandidateInactiveException("Cannot update deactivated Candidate with id: " + id);
		 }
		 
		 // whether email is changed or not
		 // if yes then with that changed email, there is profile or not
		 boolean isEmailChanging = !candidate.getEmail().equals(dto.getEmail());
		 boolean isNewEmailTaken = candidateRepo.existsByEmail(dto.getEmail());
		 
		 if(isEmailChanging && isNewEmailTaken) {
			 throw new CandidateAlreadyExistsException("Email already taken: " + dto.getEmail());
			 
		 }
		 
		 candidate.setFirstName(dto.getFirstName());
	     candidate.setLastName(dto.getLastName());
	     candidate.setEmail(dto.getEmail());
	     candidate.setPhoneNumber(dto.getPhoneNumber());
	     candidate.setDateOfBirth(dto.getDateOfBirth());
	     candidate.setGender(dto.getGender());
	     candidate.setCurrentLocation(dto.getCurrentLocation());
	     candidate.setExperienceYears(dto.getExperienceYears());
	     candidate.setCurrentJobTitle(dto.getCurrentJobTitle());
	     candidate.setCurrentSalary(dto.getCurrentSalary());
	     candidate.setExpectedSalary(dto.getExpectedSalary());
	     candidate.setSkills(dto.getSkills());
	     candidate.setUpdatedAt(LocalDateTime.now());
	     
	     Candidate updatedCandidate = candidateRepo.save(candidate);
	     return mapToResponseDTO(updatedCandidate);
		 
	 }

	 
	 //=========== deactive
	 @Override
	 @Transactional
	 public void deactivateCandidate(Long id) {
		 
		 // Step 1 — Find candidate, throw exception if not found
		 Candidate candidate = candidateRepo.findById(id).orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id: " + id));

	     // Step 2 — Check if already inactive
		 if (!candidate.isActive()) {
			 throw new CandidateInactiveException("Candidate is already deactivated with id: " + id);
		 }

		 // Step 3 — Set active to false and save
		 candidate.setActive(false);
		 candidate.setUpdatedAt(LocalDateTime.now());
		 candidateRepo.save(candidate);
	   }
	 
	 
	 //========== delete candidate
	 @Override
	 @Transactional
	 public void deleteCandidate(Long id) {
		 
		 // Step 1 — Find candidate, throw exception if not found
		 Candidate candidate = candidateRepo.findById(id).orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id: " + id));

		 // Step 2 — Delete from DB
		 candidateRepo.delete(candidate);
	    }

	 
	 
}
