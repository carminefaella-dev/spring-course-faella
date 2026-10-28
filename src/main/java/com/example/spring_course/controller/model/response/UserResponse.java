package com.example.spring_course.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String requestId;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
}
