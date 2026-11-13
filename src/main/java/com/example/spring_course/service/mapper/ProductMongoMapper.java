package com.example.spring_course.service.mapper;

import com.example.spring_course.repository.model.ProductDocument;
import com.example.spring_course.service.dto.ProductRequest;
import com.example.spring_course.service.dto.ProductResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProductMongoMapper {
    public ProductDocument toDocument(ProductRequest request) {
        ProductDocument document = new ProductDocument();

        document.setName(request.name());
        document.setDescription(request.description());
        document.setPrice(request.price());
        document.setCategory(request.category());
        document.setCreatedAt(Instant.now());
        document.setBrand(request.brand());
        document.setTechnicalDetails(request.technicalDetails());

        return document;
    }


    public ProductResponse toResponse(ProductDocument document) {
        return new ProductResponse(
                document.getId(),
                document.getName(),
                document.getDescription(),
                document.getPrice(),
                document.getCategory(),
                document.getCreatedAt(),
                document.getBrand(),
                document.getTechnicalDetails()
        );
    }
}
