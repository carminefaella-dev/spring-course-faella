package com.example.spring_course.controller.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Schema(
        description = "User's first name",
        example = "Mario"
    )
    @NotEmpty
    private String firstName;

    @Schema(
        description = "User's last name",
        example = "Rossi"
    )
    @NotEmpty
    private String lastName;

    @Schema(
        description = "Unique username used to identify the user",
        example = "mrossi"
    )
    @NotEmpty
    private String username;

    @Schema(
        description = "User's email address",
        example = "mario.rossi@example.com"
    )
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    private String email;

    @Valid
    @NotNull
    @Schema(
        description = "User's residential address"
    )
    private UserAddress address;
}
