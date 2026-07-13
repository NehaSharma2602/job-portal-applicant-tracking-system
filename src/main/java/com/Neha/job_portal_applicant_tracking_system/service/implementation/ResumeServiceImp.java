package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.ResumeRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.ResumeResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Candidate;
import com.Neha.job_portal_applicant_tracking_system.entity.Resume;
import com.Neha.job_portal_applicant_tracking_system.exception.CandidateNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.DefaultResumeAlreadyExistsException;
import com.Neha.job_portal_applicant_tracking_system.exception.ResumeNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.repository.CandidateRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.ResumeRepository;
import com.Neha.job_portal_applicant_tracking_system.service.ResumeService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResumeServiceImp implements ResumeService {

    private final ResumeRepository resumeRepo;
    private final CandidateRepository candidateRepo;
    

    //==================== mapper — resume entity → ResumeResponseDTO ===========================//
    private ResumeResponseDTO mapToResponseDTO(Resume resume) {

        ResumeResponseDTO dto = new ResumeResponseDTO();
        dto.setId(resume.getId());
        dto.setFileName(resume.getFileName());
        dto.setFileType(resume.getFileType());
        dto.setFileUrl(resume.getFileUrl());
        dto.setDefault(resume.isDefault());
        dto.setUploadedAt(resume.getUploadedAt());

        dto.setCandidateId(resume.getCandidate().getId());
        dto.setCandidateFirstName(resume.getCandidate().getFirstName());
        dto.setCandidateLastName(resume.getCandidate().getLastName());
        dto.setCandidateEmail(resume.getCandidate().getEmail());

        return dto;
    }


    //==================================upload resume ===========================================//
    @Override
    @Transactional
    public ResumeResponseDTO uploadResume(ResumeRequestDTO dto) {

        //Find candidate, throw exception if not found
        Candidate candidate = candidateRepo.findById(dto.getCandidateId())
            .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id: " + dto.getCandidateId()));

        // If this resume is marked as default,
        //          check if a default already exists
        if (dto.isDefault()) {
            boolean defaultExists = resumeRepo.existsByCandidateIdAndIsDefaultTrue(dto.getCandidateId());
            if (defaultExists) {
                throw new DefaultResumeAlreadyExistsException("A default resume already exists for candidateId: " + dto.getCandidateId()
                    + ". Please remove the existing default first.");
            }
        }

        // Create new Resume entity and set fields
        Resume resume = new Resume();
        resume.setFileName(dto.getFileName());
        resume.setFileType(dto.getFileType());
        resume.setFileUrl(dto.getFileUrl());
        resume.setDefault(dto.isDefault());
        resume.setCandidate(candidate);

        // Save and return
        Resume savedResume = resumeRepo.save(resume);
        return mapToResponseDTO(savedResume);
    }


    //=================================================== get resume by id =====================================================//
    @Override
    public ResumeResponseDTO getResumeById(Long id) {

        Resume resume = resumeRepo.findById(id).orElseThrow(
        		() -> new ResumeNotFoundException("Resume not found with id: " + id));

        return mapToResponseDTO(resume);
    }

    //====================================================get all resumes by candidates ==========================================//
    @Override
    public List<ResumeResponseDTO> getResumesByCandidateId(Long candidateId) {

        //Confirm candidate exists
        boolean candidateExists = candidateRepo.existsById(candidateId);
        if (!candidateExists) {
            throw new CandidateNotFoundException("Candidate not found with id: " + candidateId);
        }

        // Get resumes for that candidate
        List<Resume> resumes = resumeRepo.findByCandidateId(candidateId);

        if (resumes.isEmpty()) {
            throw new ResumeNotFoundException("No resumes found for candidateId: " + candidateId);
        }

        // Convert each resume to DTO
        List<ResumeResponseDTO> responseDTOs = new ArrayList<>();
        for (Resume resume : resumes) {
            ResumeResponseDTO dto = mapToResponseDTO(resume);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }


    //=================================================== get deafult resume ============================================================//
    @Override
    public ResumeResponseDTO getDefaultResume(Long candidateId) {

        // Confirm candidate exists
        boolean candidateExists = candidateRepo.existsById(candidateId);
        if (!candidateExists) {
            throw new CandidateNotFoundException("Candidate not found with id: " + candidateId);
        }

        // Get default resume
        Resume resume = resumeRepo.findByCandidateIdAndIsDefaultTrue(candidateId).orElseThrow(
        		() -> new ResumeNotFoundException("No default resume found for candidateId: " + candidateId));

        return mapToResponseDTO(resume);
    }

    //=============================================== set deafult resume ==================================//
    //============================================== removes default from old resume, sets on new one ==============================//
    @Override
    @Transactional
    public ResumeResponseDTO setDefaultResume(Long resumeId, Long candidateId) {

        // Confirm candidate exists
        boolean candidateExists = candidateRepo.existsById(candidateId);
        if (!candidateExists) {
            throw new CandidateNotFoundException("Candidate not found with id: " + candidateId);
        }

        // Find the resume to set as default
        Resume newDefault = resumeRepo.findById(resumeId).orElseThrow(
        		() -> new ResumeNotFoundException("Resume not found with id: " + resumeId));

        // If there is an existing default resume,
        //          remove default from it first
        Optional<Resume> existingDefault = resumeRepo.findByCandidateIdAndIsDefaultTrue(candidateId);

        if (existingDefault.isPresent()) {
            Resume oldDefault = existingDefault.get();
            oldDefault.setDefault(false);           
            resumeRepo.save(oldDefault);
        }

        // Set new resume as default
        newDefault.setDefault(true);
        Resume updatedResume = resumeRepo.save(newDefault);

        return mapToResponseDTO(updatedResume);
    }


    //============================================================= delete resume ================================//
    @Override
    @Transactional
    public void deleteResume(Long id) {

        Resume resume = resumeRepo.findById(id).orElseThrow(
        		() -> new ResumeNotFoundException("Resume not found with id: " + id));

        resumeRepo.delete(resume);
    }


}