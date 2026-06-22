package com.Neha.job_portal_applicant_tracking_system.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Neha.job_portal_applicant_tracking_system.dto.UserRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.UserResponseDTO;
import com.Neha.job_portal_applicant_tracking_system.entity.Role;
import com.Neha.job_portal_applicant_tracking_system.entity.User;
import com.Neha.job_portal_applicant_tracking_system.exception.EmailAlreadyExistsException;
import com.Neha.job_portal_applicant_tracking_system.exception.ResourceNotFoundException;
import com.Neha.job_portal_applicant_tracking_system.exception.UserInactiveException;
import com.Neha.job_portal_applicant_tracking_system.repository.RoleRepository;
import com.Neha.job_portal_applicant_tracking_system.repository.UserRepository;
import com.Neha.job_portal_applicant_tracking_system.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private final UserRepository userRepo;
	
	@Autowired
	private final RoleRepository roleRepo;

	public UserServiceImp(UserRepository userRepo, RoleRepository roleRepo) {
		super();
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}
	
	
	@Override
	@Transactional
	public UserResponseDTO registerUser(UserRequestDTO dto) {
		
		//duplicate email
		if(userRepo.existsByEmail(dto.getEmail())) {
			throw new EmailAlreadyExistsException("Email already registered: " + dto.getEmail());
		}
		
		Role role = roleRepo.findById(dto.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with the id: " + dto.getRoleId()));
		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setRole(role);
		user.setActive(true);
		
		User savedUser = userRepo.save(user);
		return mapToResponseDTO(savedUser);
	}


	// mapping from entity to responseDTO
	private UserResponseDTO mapToResponseDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setPhoneNumber(user.getPhoneNumber());
		dto.setActive(user.isActive());
		dto.setRoleName(user.getRole().getRole());
		dto.setCreatedAt(user.getCreatedAt());
		return dto;
	}
	
	//get user by id
	@Override
	public UserResponseDTO getUserById(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		
		if(!user.isActive()) {
			throw new UserInactiveException("User account is deactivated for id: " + id);
		}
		return mapToResponseDTO(user);
		
	}
	
	//get user by email
	@Override
	public UserResponseDTO getUserByEmail(String email) {
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with email: " + email));
		
		if(!user.isActive()) {
			throw new UserInactiveException("User account is deactivated for email: " + email);
		}
		return mapToResponseDTO(user);
	}
	
	//get all user
	@Override
	public List<UserResponseDTO> getAllUser() {
		List<User> users = userRepo.findAll();
		List<UserResponseDTO> result = new ArrayList<>();
		
		if(users.isEmpty()) {
			throw new ResourceNotFoundException("No user found");
		}
		
	    for (User user : users) {
	        result.add(mapToResponseDTO(user));
	    }
	    
	    return result;
	}
	
	//update 
	@Transactional
	@Override
	public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

		if (!user.isActive()) {
		    throw new UserInactiveException("Cannot update a deactivated user with id: " + id);
		 };
		 
		 // if email changes then it should not be taken by someone else
		 if(!user.getEmail().equals(dto.getEmail()) && userRepo.existsByEmail(dto.getEmail())) {
			 throw new EmailAlreadyExistsException("Email already in use: " + dto.getEmail());
		 }
		 
		 if(dto.getRoleId() != null) {
			 Role role = roleRepo.findById(dto.getRoleId())
					 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + dto.getRoleId()));
			 
			 user.setRole(role);
		 }
		 user.setFirstName(dto.getFirstName());
	        user.setLastName(dto.getLastName());
	        user.setEmail(dto.getEmail());
	        user.setPhoneNumber(dto.getPhoneNumber());
	        user.setUpdatedAt(LocalDateTime.now());

	        return mapToResponseDTO(userRepo.save(user));
	}
	
	@Override
    @Transactional
    public void deactivateUser(Long id) {

        User user = userRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "User not found with id: " + id));

        if (!user.isActive()) {
            throw new UserInactiveException(
                "User is already deactivated with id: " + id);
        }

        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);
    }
	
	@Override
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "User not found with id: " + id));

        userRepo.delete(user);
    }
}
