package com.example.spring_course.service.impl;

import com.example.spring_course.model.User;
import com.example.spring_course.model.request.UserRequest;
import com.example.spring_course.model.response.UserResponse;

import java.util.List;

public interface UserServiceImpl {
    UserResponse createUser(UserRequest request);
    List<User> getAllUser();
    User findByEmail(String email);
    User updateUser(String email, UserRequest request);
    UserResponse deleteByEmail(String email);
}
