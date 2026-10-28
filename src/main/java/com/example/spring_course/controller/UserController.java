package com.example.spring_course.controller;

import com.example.spring_course.controller.model.request.UserRequest;
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
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/getUserByName/{firstName}")
    public ResponseEntity<List<UserResponseDTO>> getUserByName(@PathVariable String firstName) {
        return ResponseEntity.ok(userService.searchByName(firstName));
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<List<UserResponseDTO>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.searchByEmail(email));
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequest request) {
        UserRequestDTO requestDTO = userMapper.mapRequest(request);
        UserResponseDTO responseDTO = userService.createUser(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/updateUser/{email}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String email, @RequestBody @Valid UserRequest request) {

        UserRequestDTO requestDTO = userMapper.mapRequest(request);
        UserResponseDTO responseDTO = userService.updateUser(email, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable String email) {
        UserResponseDTO responseDTO = userService.deleteByEmail(email);

        return ResponseEntity.ok(responseDTO);
    }
}
