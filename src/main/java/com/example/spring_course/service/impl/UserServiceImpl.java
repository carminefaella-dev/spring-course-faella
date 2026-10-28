package com.example.spring_course.service.impl;

import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;

import java.util.List;

public interface UserServiceImpl {
    UserResponseDTO createUser(UserRequestDTO requestDTO);
    UserResponseDTO updateUser(String email, UserRequestDTO requestDTO);
    UserResponseDTO deleteByEmail(String email);
    List<UserResponseDTO> getAllUser();
    List<UserResponseDTO> searchByName(String firstName);
    List<UserResponseDTO> searchByEmail(String email);
}
