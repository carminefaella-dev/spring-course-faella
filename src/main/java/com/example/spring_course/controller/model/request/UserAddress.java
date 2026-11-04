package com.example.spring_course.controller.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserAddress {
    @Schema(
        description = "City name",
        example = "Milan"
    )
    @NotEmpty
    private String city;

    @Schema(
        description = "Street name",
        example = "Via Roma"
    )
    @NotEmpty
    private String streetName;

    @Schema(
        description = "Street number",
        example = "25"
    )
    @NotEmpty
    private String streetNumber;

    @Schema(
        description = "Postal code",
        example = "20100"
    )
    @NotEmpty
    private String postalCode;

    @Schema(
        description = "Province code consisting of two uppercase letters",
        example = "MI"
    )
    @NotEmpty
    @Pattern(regexp = "([A-Z]{2})")
    private String province;

    @Schema(
        description = "Country name",
        example = "Italy"
    )
    @NotEmpty
    private String country;
}
