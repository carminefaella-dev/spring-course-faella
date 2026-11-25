package com.example.spring_course.service;

import com.example.spring_course.repository.ProductMongoRepository;
import com.example.spring_course.repository.model.ProductDocument;
import com.example.spring_course.controller.model.request.ProductMongoRequest;
import com.example.spring_course.controller.model.response.ProductMongoResponse;
import com.example.spring_course.service.mapper.ProductMongoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMongoService {
    private final ProductMongoRepository repository;
    private final ProductMongoMapper mapper;

    public ProductMongoResponse create(ProductMongoRequest request) {
        ProductDocument document = mapper.toDocument(request);
        ProductDocument saved = repository.save(document);
        return mapper.toResponse(saved);
    }


    public List<ProductMongoResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    public ProductMongoResponse findById(String id) {
        ProductDocument document = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found: " + id)
                );

        return mapper.toResponse(document);
    }


    public ProductMongoResponse update(String id, ProductMongoRequest request) {
        ProductDocument document = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found: " + id)
                );

        document.setName(request.name());
        document.setDescription(request.description());
        document.setPrice(request.price());
        document.setCategory(request.category());

        ProductDocument updated = repository.save(document);

        return mapper.toResponse(updated);
    }


    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Product not found: " + id
            );
        }

        repository.deleteById(id);
    }


    public List<ProductMongoResponse> findByCategory(String category) {
        return repository.findByCategory(category)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    public List<ProductMongoResponse> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
