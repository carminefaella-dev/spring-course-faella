package com.example.spring_course.service.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String address;
}
