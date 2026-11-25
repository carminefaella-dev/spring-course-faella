package com.example.spring_course.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String takenType;
    private Long expiresIn;
}
