package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.repository.UserRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import com.example.spring_course.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserServiceImpl {
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        log.info("Creating user with email: {}", requestDTO.getEmail());

        String email = requestDTO.getEmail().toLowerCase();

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    log.debug("User not found. Creating new user with email: {}", email);

                    UserEntity newUser = new UserEntity();

                    newUser.setUsername(requestDTO.getUsername());
                    newUser.setEmail(email);
                    newUser.setFirstName(requestDTO.getFirstName());
                    newUser.setLastName(requestDTO.getLastName());
                    newUser.setAddress(requestDTO.getAddress());

                    return userRepository.save(newUser);
                });

        log.info("User creation completed for email: {}", email);

        UserResponseDTO response = mapToDto(user);
        response.setRequestId(UUID.randomUUID().toString());

        return response;
    }

    @Override
    public UserResponseDTO updateUser(String email, UserRequestDTO requestDTO) {
        log.info("Updating user with email: {}", email);

        UserEntity user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> {
                    log.warn("User not found for update. Email: {}", email);
                    return new AppException("User not found", HttpStatus.NOT_FOUND);
                });


        user.setUsername(requestDTO.getUsername());
        user.setEmail(requestDTO.getEmail().toLowerCase());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setAddress(requestDTO.getAddress());

        userRepository.save(user);

        log.info("User successfully updated: {}", user.getEmail());

        UserResponseDTO response = mapToDto(user);
        response.setRequestId(UUID.randomUUID().toString());

        return response;
    }

    @Override
    public UserResponseDTO deleteByEmail(String email) {
        log.info("Deleting user with email: {}", email);

        UserEntity user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> {
                    log.warn("User not found for deletion. Email: {}", email);
                    return new AppException("User not found", HttpStatus.NOT_FOUND);
                });

        userRepository.delete(user);

        log.info("User successfully deleted: {}", email);

        UserResponseDTO response = mapToDto(user);
        response.setRequestId(UUID.randomUUID().toString());

        return response;
    }

    @Override
    public List<UserResponseDTO> getAllUser() {
        log.info("Retrieving all users");

        List<UserResponseDTO> users = userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponseDTO dto = mapToDto(user);
                    dto.setRequestId(UUID.randomUUID().toString());
                    return dto;
                })
                .toList();

        log.info("Retrieved {} users", users.size());

        return users;
    }

    @Override
    public List<UserResponseDTO> searchByName(String firstName) {
        log.info("Searching users by first name: {}", firstName);

        List<UserEntity> users = userRepository.searchByName(firstName);

        log.debug("Search for firstName returned {} users", users.size());

        return getUserResponse(users);
    }

    @Override
    public List<UserResponseDTO> searchByEmail(String email) {
        log.info("Searching user by email: {}", email);

        List<UserEntity> users = userRepository.searchByEmail(email);

        log.debug("Search for email returned {} users", users.size());

        return getUserResponse(users);
    }

    @NonNull
    private List<UserResponseDTO> getUserResponse(List<UserEntity> users) {
        if (users.isEmpty()) {
            log.warn("No users found");

            throw new AppException(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );
        }

        log.debug("Mapping {} users to DTO", users.size());

        return users.stream()
                .map(user -> {
                    UserResponseDTO dto = mapToDto(user);
                    dto.setRequestId(UUID.randomUUID().toString());
                    return dto;
                })
                .toList();
    }

    private UserResponseDTO mapToDto(UserEntity user) {
        log.debug("Mapping UserEntity to UserResponseDTO for email: {}", user.getEmail());

        UserResponseDTO dto = new UserResponseDTO();

        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAddress(user.getAddress());

        return dto;
    }
}
