package com.example.spring_course.service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String address;
}
