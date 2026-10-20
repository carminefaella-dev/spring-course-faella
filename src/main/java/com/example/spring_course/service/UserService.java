package com.example.spring_course.service;

import com.example.spring_course.model.User;
import com.example.spring_course.model.request.UserRequest;
import com.example.spring_course.model.response.UserResponse;
import com.example.spring_course.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserServiceImpl {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public UserResponse createUser(UserRequest request) {
        if (users.containsKey(request.getEmail())) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCity(request.getCity());
        user.setState(request.getState());

        users.put(user.getEmail(), user);

        UserResponse response = new UserResponse();
        response.setRequestId(UUID.randomUUID().toString());
        response.setEmail(user.getEmail());

        return response;
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
    public User updateUser(String email, UserRequest request) {
        User user = users.get(email.toLowerCase());

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        user.setUsername(request.getUsername().trim());
        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setCity(request.getCity());
        user.setState(request.getState());

        return user;
    }

    @Override
    public UserResponse deleteByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (!users.containsKey(email.toLowerCase())) {
            throw new NoSuchElementException("User not found");
        }

        users.remove(email.toLowerCase());

        UserResponse response = new UserResponse();
        response.setRequestId(UUID.randomUUID().toString());
        response.setEmail(email);

        return response;
    }
}
