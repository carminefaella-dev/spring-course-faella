package com.example.spring_course.service.impl;

import com.example.spring_course.dto.UserRequestDTO;
import com.example.spring_course.dto.UserResponseDTO;
import com.example.spring_course.model.User;
import com.example.spring_course.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final List<User> users = new ArrayList<>();

    @Override
    public List<UserResponseDTO> findAll() {
        return users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getEmail()))
                .toList();
    }

    @Override
    public UserResponseDTO findByEmail(String email) {
        User user = users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getEmail()
        );
    }

    @Override
    public UserResponseDTO create(UserRequestDTO request) {
        User user = User.builder()
                .id((long) (users.size() + 1))
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        users.add(user);

        return new UserResponseDTO(
                user.getId(),
                user.getEmail()
        );
    }
}