package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.repository.UserRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import com.example.spring_course.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl {
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        String email = requestDTO.getEmail().toLowerCase();

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    UserEntity newUser = new UserEntity();

                    newUser.setUsername(requestDTO.getUsername());
                    newUser.setEmail(email);
                    newUser.setFirstName(requestDTO.getFirstName());
                    newUser.setLastName(requestDTO.getLastName());
                    newUser.setAddress(requestDTO.getAddress());

                    return userRepository.save(newUser);
                });

        UserResponseDTO response = mapToDto(user);
        response.setRequestId(UUID.randomUUID().toString());

        return response;
    }

    @Override
    public UserResponseDTO updateUser(String email, UserRequestDTO requestDTO) {
        UserEntity user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() ->
                        new AppException("User not found", HttpStatus.NOT_FOUND));

        user.setUsername(requestDTO.getUsername());
        user.setEmail(requestDTO.getEmail().toLowerCase());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setAddress(requestDTO.getAddress());

        userRepository.save(user);

        UserResponseDTO response = mapToDto(user);
        response.setRequestId(UUID.randomUUID().toString());

        return response;
    }

    @Override
    public UserResponseDTO deleteByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() ->
                        new AppException("User not found", HttpStatus.NOT_FOUND));

        userRepository.delete(user);

        UserResponseDTO response = mapToDto(user);
        response.setRequestId(UUID.randomUUID().toString());

        return response;
    }

    @Override
    public List<UserResponseDTO> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponseDTO dto = mapToDto(user);
                    dto.setRequestId(UUID.randomUUID().toString());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<UserResponseDTO> searchByName(String firstName) {
        List<UserEntity> users = userRepository.searchByName(firstName);
        return getUserResponse(users);
    }

    @Override
    public List<UserResponseDTO> searchByEmail(String email) {
        List<UserEntity> users = userRepository.searchByEmail(email);
        return getUserResponse(users);
    }

    @NonNull
    private List<UserResponseDTO> getUserResponse(List<UserEntity> users) {
        if (users.isEmpty()) {
            throw new AppException(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );
        }

        return users.stream()
                .map(user -> {
                    UserResponseDTO dto = mapToDto(user);
                    dto.setRequestId(UUID.randomUUID().toString());
                    return dto;
                })
                .toList();
    }

    private UserResponseDTO mapToDto(UserEntity user) {
        UserResponseDTO dto = new UserResponseDTO();

        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAddress(user.getAddress());

        return dto;
    }
}
