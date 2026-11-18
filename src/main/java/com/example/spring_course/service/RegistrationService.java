package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.repository.UserAccountRepository;
import com.example.spring_course.repository.model.UserAccount;
import com.example.spring_course.service.dto.RegisterRequest;
import com.example.spring_course.service.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new AppException("Username già utilizzato", HttpStatus.CONFLICT);
        }

        UserAccount user = new UserAccount(
                request.username(),
                passwordEncoder.encode(request.password()),
                "USER"
        );

        UserAccount savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }
}