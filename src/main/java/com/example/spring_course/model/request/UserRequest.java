package com.example.spring_course.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Pattern(regexp = "([A-Z]{2})")
    private String city;

    @NotEmpty
    @Pattern(regexp = "([A-Z]{2})")
    private String state;
}
