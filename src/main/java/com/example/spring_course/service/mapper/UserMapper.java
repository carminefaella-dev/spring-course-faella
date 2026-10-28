package com.example.spring_course.service.mapper;

import com.example.spring_course.controller.model.request.UserAddress;
import com.example.spring_course.controller.model.request.UserRequest;
import com.example.spring_course.controller.model.response.UserResponse;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "address", source = "address")
    public abstract UserRequestDTO mapRequest(UserRequest request);

    @Mapping(target = "credential", expression = "java(buildCredential(response))")
    public abstract UserResponseDTO mapResponse(UserResponse response);

    protected String buildAddress(UserAddress address) {
        if (address == null) {
            return null;
        }

        return String.format(
                "%s %s, %s %s (%s), %s",
                address.getStreetName(),
                address.getStreetNumber(),
                address.getPostalCode(),
                address.getCity(),
                address.getProvince(),
                address.getCountry()
        );
    }

    protected String buildCredential(UserResponse response) {
        if (response == null) {
            return null;
        }

        return String.format(
                "%s|%s|%s|%s",
                response.getEmail(),
                response.getUsername(),
                response.getFirstName(),
                response.getLastName()
        );
    }
}
