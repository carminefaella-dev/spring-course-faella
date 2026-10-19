package com.example.spring_course.controller;

import com.example.spring_course.dto.UserRequestDTO;
import com.example.spring_course.dto.UserResponseDTO;
import com.example.spring_course.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/manageUser/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getUser")
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/getUser/{email}")
    public UserResponseDTO findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PostMapping
    public UserResponseDTO create(@RequestBody @Valid UserRequestDTO request) {
        return userService.create(request);
    }
}
