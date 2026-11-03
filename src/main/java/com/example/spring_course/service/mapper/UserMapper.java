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
    @Mapping(
            target = "address",
            expression = "java(buildAddress(request.getAddress()))"
    )
    public abstract UserRequestDTO mapRequest(UserRequest request);

    @Mapping(
            target = "fullName",
            expression = "java(dto.getFirstName() + \" \" + dto.getLastName())"
    )
    public abstract UserResponse mapResponse(UserResponseDTO dto);

    protected String buildAddress(UserAddress address) {
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
}
