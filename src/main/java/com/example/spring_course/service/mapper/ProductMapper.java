package com.example.spring_course.service.mapper;

import com.example.spring_course.controller.model.request.ProductRequest;
import com.example.spring_course.controller.model.response.ProductResponse;
import com.example.spring_course.repository.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequest request) {
        Product product = new Product();

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setCategory(request.category());
        product.setBrand(request.brand());

        return product;
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getBrand()
        );
    }
}