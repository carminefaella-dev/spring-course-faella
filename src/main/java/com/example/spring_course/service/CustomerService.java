package com.example.spring_course.service;

import com.example.spring_course.controller.model.response.CustomerResponse;
import com.example.spring_course.exception.AppException;
import com.example.spring_course.repository.CustomerRepository;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        CustomerEntity entity = repository.findById(id).orElseThrow(()->
                new AppException("Customer: " + id + "not found", HttpStatus.NOT_FOUND)
        );

        return mapper.mapResponse(mapper.mapDto(entity));
    }
}
