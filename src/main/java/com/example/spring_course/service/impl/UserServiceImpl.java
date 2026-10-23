package com.example.spring_course.service.impl;

import com.example.spring_course.model.User;
import com.example.spring_course.model.response.UserResponse;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;

import java.util.List;

public interface UserServiceImpl {
    UserResponse createUser(UserRequestDTO requestDTO);
    List<User> getAllUser();
    User findByEmail(String email);
    UserResponse updateUser(String email, UserRequestDTO requestDTO);
    UserResponse deleteByEmail(String email);
}
