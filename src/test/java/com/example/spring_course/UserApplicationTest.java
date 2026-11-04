package com.example.spring_course;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.repository.UserRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.UserService;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserApplicationTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserWhenEmailDoesNotExist() {
        UserRequestDTO request = new UserRequestDTO();
        request.setUsername("cross");
        request.setEmail("mario.rossi@example.com");
        request.setFirstName("Mario");
        request.setLastName("Rossi");
        request.setAddress("Via Roma 1");

        UserEntity savedUser = new UserEntity();
        savedUser.setUsername("cross");
        savedUser.setEmail("mario.rossi@example.com");
        savedUser.setFirstName("Mario");
        savedUser.setLastName("Rossi");
        savedUser.setAddress("Via Roma 1");

        when(userRepository.findByEmail("mario.rossi@example.com"))
                .thenReturn(Optional.empty());

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedUser);

        UserResponseDTO response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("mario.rossi@example.com", response.getEmail());
        assertEquals("Mario", response.getFirstName());
        assertEquals("Rossi", response.getLastName());
        assertNotNull(response.getRequestId());

        verify(userRepository).findByEmail("mario.rossi@example.com");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingUser() {
        String email = "mario.rossi@example.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(
                AppException.class,
                () -> userService.updateUser(email, new UserRequestDTO())
        );

        assertEquals("User not found", exception.getMessage());
    }
}
