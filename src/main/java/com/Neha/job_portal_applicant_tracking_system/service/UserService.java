package com.Neha.job_portal_applicant_tracking_system.service;

import java.util.List;

import com.Neha.job_portal_applicant_tracking_system.dto.UserRequestDTO;
import com.Neha.job_portal_applicant_tracking_system.dto.UserResponseDTO;

/**
 * Service interface for managing User operations.
 * Handles business logic for registering, retrieving,
 * updating, and deleting users.
 */
public interface UserService {

    /**
     * Registers a new user in the system.
     *
     * @param userRequestDTO the user details from the request body
     * @return UserResponseDTO the saved user details
     * @throws EmailAlreadyExistsException if the email is already registered
     * @throws ResourceNotFoundException if the provided role does not exist
     */
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return UserResponseDTO the user details
     * @throws ResourceNotFoundException if no user is found with the given ID
     * @throws UserInactiveException if the user account is deactivated
     */
    UserResponseDTO getUserById(Long id);

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email of the user
     * @return UserResponseDTO the user details
     * @throws ResourceNotFoundException if no user is found with the given email
     * @throws UserInactiveException if the user account is deactivated
     */
    UserResponseDTO getUserByEmail(String email);

    /**
     * Retrieves all users in the system.
     *
     * @return List of UserResponseDTO
     * @throws ResourceNotFoundException if no users exist
     */
    List<UserResponseDTO> getAllUsers();

    /**
     * Updates an existing user's details.
     *
     * @param id             the ID of the user to update
     * @param userRequestDTO the updated user details
     * @return UserResponseDTO the updated user details
     * @throws ResourceNotFoundException   if no user is found with the given ID
     * @throws UserInactiveException       if the user account is deactivated
     * @throws EmailAlreadyExistsException if the new email is already taken
     */
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    /**
     * Deactivates a user account — sets active = false.
     * The user still exists in the database (soft delete).
     *
     * @param id the ID of the user to deactivate
     * @throws ResourceNotFoundException if no user is found with the given ID
     * @throws UserInactiveException     if the user is already deactivated
     */
    void deactivateUser(Long id);

    /**
     * Permanently deletes a user from the system.
     *
     * @param id the ID of the user to delete
     * @throws ResourceNotFoundException if no user is found with the given ID
     */
    void deleteUser(Long id);

    /**
     * Checks whether an email is already registered.
     *
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    boolean emailExists(String email);
}
