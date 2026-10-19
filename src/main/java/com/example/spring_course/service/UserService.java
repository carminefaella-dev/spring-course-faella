package com.example.spring_course.service;

import com.example.spring_course.dto.UserRequestDTO;
import com.example.spring_course.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> findAll();
    UserResponseDTO findByEmail(String email);
    UserResponseDTO create(UserRequestDTO request);
}
