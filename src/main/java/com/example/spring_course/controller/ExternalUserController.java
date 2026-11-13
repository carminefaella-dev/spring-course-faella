package com.example.spring_course.controller;

import com.example.spring_course.controller.model.request.ExternalUserRequest;
import com.example.spring_course.controller.model.response.ExternalUserResponse;
import com.example.spring_course.service.ExternalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/external-users")
@RequiredArgsConstructor
public class ExternalUserController {
    private final ExternalUserService service;

    @GetMapping
    public ResponseEntity<List<ExternalUserResponse>> findAllUsers() {
        return ResponseEntity.ok(service.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExternalUserResponse> findUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.findUser(id));
    }

    @PostMapping
    public ResponseEntity<ExternalUserResponse> createUser(@RequestBody ExternalUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExternalUserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody ExternalUserRequest request) {
        return ResponseEntity.ok(service.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
