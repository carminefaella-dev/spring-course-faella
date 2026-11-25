package com.example.spring_course.controller;

import com.example.spring_course.controller.model.request.ProductRequest;
import com.example.spring_course.controller.model.response.ProductResponse;
import com.example.spring_course.service.ProductAsyncService;
import com.example.spring_course.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final ProductAsyncService productAsyncService;

    public ProductController(ProductService productService,  ProductAsyncService productAsyncService) {
        this.productService = productService;
        this.productAsyncService = productAsyncService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    @GetMapping("/{id}/async")
    public CompletableFuture<ProductResponse> findByIdAsync(@PathVariable Long id) {
        log.info("CONTROLLER START - thread={}", Thread.currentThread().getName());

        CompletableFuture<ProductResponse> future = productAsyncService.findById(id);

        log.info("CONTROLLER END - thread={}", Thread.currentThread().getName());

        return future;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}