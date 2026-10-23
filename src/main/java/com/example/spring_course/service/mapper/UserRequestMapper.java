package com.example.spring_course.service.mapper;

import com.example.spring_course.model.request.UserAddress;
import com.example.spring_course.model.request.UserRequest;
import com.example.spring_course.service.dto.UserRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserRequestMapper {
    @Mapping(target = "address", source = "address")
    public abstract UserRequestDTO mapRequest(UserRequest request);

    protected String map(UserAddress address) {
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
