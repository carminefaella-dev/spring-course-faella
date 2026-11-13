package com.example.spring_course.controller.model.request;

import lombok.Data;

@Data
public class ExternalUserRequest {
    private String name;
    private String username;
    private String email;
}