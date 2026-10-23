package com.example.spring_course.service;

import com.example.spring_course.model.User;
import com.example.spring_course.model.response.UserResponse;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserServiceImpl {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public UserResponse createUser(UserRequestDTO requestDTO) {
        String email = requestDTO.getEmail().toLowerCase();

        if (users.containsKey(email)) {
            throw new RuntimeException(
                    "User already exists with email: " + email);
        }

        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setEmail(email);
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setAddress(requestDTO.getAddress());

        users.put(email, user);

        return new UserResponse(
                UUID.randomUUID().toString(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    @Override
    public List<User> getAllUser() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        User user = users.get(email.toLowerCase());

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        return user;
    }

    @Override
    public UserResponse updateUser(String email, UserRequestDTO requestDTO) {
        User user = users.get(email.toLowerCase());

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        user.setUsername(requestDTO.getUsername());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());

        UserResponse response = new UserResponse();

        response.setRequestId(UUID.randomUUID().toString());
        response.setEmail(email);
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());

        return response;
    }

    @Override
    public UserResponse deleteByEmail(String email) {
        email = email.toLowerCase();

        User user = users.remove(email);

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        UserResponse response = new UserResponse();

        response.setRequestId(UUID.randomUUID().toString());
        response.setEmail(email);
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());

        return response;
    }
}
