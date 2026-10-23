package com.example.spring_course.controller;

import com.example.spring_course.model.User;
import com.example.spring_course.model.request.UserRequest;
import com.example.spring_course.model.response.UserResponse;
import com.example.spring_course.service.UserService;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import com.example.spring_course.service.mapper.UserRequestMapper;
import com.example.spring_course.service.mapper.UserResponseMapper;
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
    private final UserRequestMapper reqMapper;
    private final UserResponseMapper resMapper;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequest request) {
        UserRequestDTO requestDTO = reqMapper.mapRequest(request);

        UserResponse response = userService.createUser(requestDTO);

        UserResponseDTO responseDTO = resMapper.mapResponse(response);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/updateUser/{email}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String email, @RequestBody @Valid UserRequest request) {
        UserRequestDTO requestDTO = reqMapper.mapRequest(request);

        UserResponse response = userService.updateUser(email, requestDTO);

        UserResponseDTO responseDTO = resMapper.mapResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable String email) {
        UserResponse response = userService.deleteByEmail(email);

        UserResponseDTO responseDTO = resMapper.mapResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
