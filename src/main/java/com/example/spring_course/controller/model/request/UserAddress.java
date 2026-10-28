package com.example.spring_course.controller.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserAddress {
    @NotEmpty
    private String city;

    @NotEmpty
    private String streetName;

    @NotEmpty
    private String streetNumber;

    @NotEmpty
    private String postalCode;

    @NotEmpty
    @Pattern(regexp = "([A-Z]{2})")
    private String province;

    @NotEmpty
    private String country;
}
