package com.example.spring_course.service;

import com.example.spring_course.client.ExternalUserClient;
import com.example.spring_course.controller.model.request.ExternalUserRequest;
import com.example.spring_course.controller.model.response.ExternalUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalUserService {
    private final ExternalUserClient externalUserClient;

    public List<ExternalUserResponse> findAllUsers() {
        return externalUserClient.findAllUsers();
    }

    public ExternalUserResponse findUser(Long id) {
        return externalUserClient.findUser(id);
    }

    public ExternalUserResponse createUser(ExternalUserRequest request) {
        return externalUserClient.createUser(request);
    }

    public ExternalUserResponse updateUser(Long id, ExternalUserRequest request) {
        return externalUserClient.updateUser(id, request);
    }

    public void deleteUser(Long id) {
        externalUserClient.deleteUser(id);
    }
}
