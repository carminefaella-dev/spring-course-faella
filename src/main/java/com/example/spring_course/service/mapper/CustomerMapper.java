package com.example.spring_course.service.mapper;

import com.example.spring_course.controller.model.response.CustomerResponse;
import com.example.spring_course.controller.model.response.OrderResponse;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.service.dto.CustomerDTO;
import com.example.spring_course.service.dto.OrderDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public class CustomerMapper {
    public CustomerResponse mapResponse(CustomerDTO customerDto) {
        List<OrderResponse> orders = customerDto.getOrders()
                .stream()
                .map(orderDTO -> new OrderResponse(
                        orderDTO.getId(), orderDTO.getTotal()
                ))
                .toList();

        return new CustomerResponse(customerDto.getId(), customerDto.getName(), orders);
    }

    public CustomerDTO mapDto(CustomerEntity entity) {
        List<OrderDTO> orders = entity.getOrders()
                .stream()
                .map(orderDTO -> new OrderDTO(
                        orderDTO.getId(), orderDTO.getTotal()
                ))
                .toList();

        return new CustomerDTO(entity.getId(), entity.getName(), orders);
    }
}
