package com.example.spring_course.controller;

import com.example.spring_course.controller.model.request.LoginRequest;
import com.example.spring_course.controller.model.response.TokenResponse;
import com.example.spring_course.service.RegistrationService;
import com.example.spring_course.service.TokenService;
import com.example.spring_course.service.dto.RegisterRequest;
import com.example.spring_course.service.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public AuthController(RegistrationService registrationService, AuthenticationManager authManager, TokenService tokenService) {
        this.registrationService = registrationService;
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = registrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody LoginRequest request) {
        try {
            var auth = authManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword())
            );
            return ResponseEntity.ok().header("Cache-Control", "no-store")
                    .body(tokenService.issue(auth));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }
    }
}