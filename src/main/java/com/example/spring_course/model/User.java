package com.example.spring_course.model;

import lombok.Data;

@Data
public class User {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String state;
}
