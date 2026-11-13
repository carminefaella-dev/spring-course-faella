package com.example.spring_course.client;

import com.example.spring_course.controller.model.request.ExternalUserRequest;
import com.example.spring_course.controller.model.response.ExternalUserResponse;
import com.example.spring_course.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExternalUserClient {
    private final RestClient restClient;

    public List<ExternalUserResponse> findAllUsers() {
        try {
            return restClient.get()
                    .uri("/user")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (HttpClientErrorException e) {
            throw new AppException(e.getMessage(), HttpStatus.valueOf(e.getStatusCode().value()));
        } catch (RestClientException e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ExternalUserResponse findUser(Long id) {
        try {
            return restClient.get()
                    .uri("/user/{id}", id)
                    .retrieve()
                    .body(ExternalUserResponse.class);
        } catch (HttpClientErrorException e) {
            throw new AppException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RestClientException e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ExternalUserResponse createUser(ExternalUserRequest request) {
        try {
            return restClient.post()
                    .uri("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(ExternalUserResponse.class);
        } catch (HttpClientErrorException e) {
            throw new AppException(e.getMessage(), HttpStatus.valueOf(e.getStatusCode().value()));
        } catch (RestClientException e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ExternalUserResponse updateUser(Long id, ExternalUserRequest request) {
        try {
            return restClient.put()
                    .uri("/user/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(ExternalUserResponse.class);
        } catch (HttpClientErrorException e) {
            throw new AppException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RestClientException e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteUser(Long id) {
        try {
            restClient.delete()
                    .uri("/user/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException e) {
            throw new AppException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RestClientException e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}