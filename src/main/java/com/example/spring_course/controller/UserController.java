package com.example.spring_course.controller;

import com.example.spring_course.controller.model.request.UserRequest;
import com.example.spring_course.controller.model.response.UserResponse;
import com.example.spring_course.service.UserService;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import com.example.spring_course.service.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> responses = userService.getAllUser()
                .stream()
                .map(userMapper::mapResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/getUserByName/{firstName}")
    public ResponseEntity<List<UserResponse>> getUserByName(
            @PathVariable String firstName) {
        List<UserResponse> responses = userService.searchByName(firstName)
                .stream()
                .map(userMapper::mapResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<List<UserResponse>> getUserByEmail(
            @PathVariable String email) {
        List<UserResponse> responses = userService.searchByEmail(email)
                .stream()
                .map(userMapper::mapResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        UserRequestDTO requestDTO = userMapper.mapRequest(request);
        UserResponseDTO responseDTO = userService.createUser(requestDTO);
        UserResponse response = userMapper.mapResponse(responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateUser/{email}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String email, @RequestBody @Valid UserRequest request) {
        UserRequestDTO requestDTO = userMapper.mapRequest(request);
        UserResponseDTO responseDTO = userService.updateUser(email, requestDTO);
        UserResponse response = userMapper.mapResponse(responseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String email) {
        UserResponseDTO responseDTO = userService.deleteByEmail(email);
        UserResponse response = userMapper.mapResponse(responseDTO);
        return ResponseEntity.ok(response);
    }
}
