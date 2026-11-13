package com.example.spring_course.controller.model.response;

import lombok.Data;

@Data
public class ExternalUserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
}
