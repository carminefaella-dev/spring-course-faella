package com.example.spring_course.controller;

import com.example.spring_course.model.User;
import com.example.spring_course.model.request.UserRequest;
import com.example.spring_course.model.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping ("/api/v1")
public class UserController {
    @GetMapping("/getUser")
    public ResponseEntity<User> getUser() {
        User user = new User();
        user.setUsername("prova01");
        user.setEmail("prova@email.com");
        user.setFirstName("Nome");
        user.setLastName("Cognome");
        user.setCity("MI");
        user.setState("LO");
        user.setCountry("IT");

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setCountry(request.getState());

        UserResponse response = new UserResponse();
        response.setRequestId(UUID.randomUUID().toString());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
