package com.example.spring_course.service;

import com.example.spring_course.controller.model.response.UserResponse;
import com.example.spring_course.repository.UserRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import com.example.spring_course.service.impl.UserServiceImpl;
import com.example.spring_course.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

        UserResponseDTO response = userMapper.mapResponse(mapUserResponse(user));
        response.setRequestId(UUID.randomUUID().toString());

        return response;
    }

    @Override
    public UserResponseDTO updateUser(String email, UserRequestDTO requestDTO) {
        UserEntity user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setUsername(requestDTO.getUsername());
        user.setEmail(requestDTO.getEmail().toLowerCase());
        user.setAddress(requestDTO.getAddress());

        userRepository.save(user);

        UserResponseDTO response = userMapper.mapResponse(mapUserResponse(user));
        response.setRequestId(UUID.randomUUID().toString());

        return response;
    }

    @Override
    public UserResponseDTO deleteByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        userRepository.delete(user);

        UserResponseDTO response = userMapper.mapResponse(mapUserResponse(user));
        response.setRequestId(UUID.randomUUID().toString());

        return response;
    }

    @Override
    public List<UserResponseDTO> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponseDTO dto = userMapper.mapResponse(mapUserResponse(user));
                    dto.setRequestId(UUID.randomUUID().toString());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<UserResponseDTO> searchByName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }

        List<UserEntity> users = userRepository.searchByName(firstName);

        if (users.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        return users.stream()
                .map(user -> {
                    UserResponseDTO dto = userMapper.mapResponse(mapUserResponse(user));
                    dto.setRequestId(UUID.randomUUID().toString());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<UserResponseDTO> searchByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        List<UserEntity> users = userRepository.searchByEmail(email);

        if (users.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        return users.stream()
                .map(user -> {
                    UserResponseDTO dto = userMapper.mapResponse(mapUserResponse(user));
                    dto.setRequestId(UUID.randomUUID().toString());
                    return dto;
                })
                .toList();
    }

    private UserResponse mapUserResponse(UserEntity user) {
        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        return response;
    }
}
