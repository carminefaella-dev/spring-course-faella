package com.example.spring_course.controller;

import com.example.spring_course.model.User;
import com.example.spring_course.model.request.UserRequest;
import com.example.spring_course.model.response.UserResponse;
import com.example.spring_course.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

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
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateUser/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody @Valid UserRequest request) {
        User updatedUser = userService.updateUser(email, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String email) {
        UserResponse response = userService.deleteByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
