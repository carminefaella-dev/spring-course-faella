package com.example.spring_course.service.mapper;

import com.example.spring_course.model.response.UserResponse;
import com.example.spring_course.service.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class UserResponseMapper {
    @Mapping(
            target = "fullName",
            source = ".",
            qualifiedByName = "buildFullName"
    )
    public abstract UserResponseDTO mapResponse(UserResponse userResponse);

    @Named("buildFullName")
    protected String buildFullName(UserResponse response) {
        return response.getFirstName() + " - " +
                response.getLastName();
    }
}
