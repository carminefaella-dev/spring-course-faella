package com.example.spring_course.service;

import com.example.spring_course.controller.model.response.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductAsyncService {
    private static final Logger log = LoggerFactory.getLogger(ProductAsyncService.class);

    private final ProductService productService;

    public ProductAsyncService(ProductService productService) {
        this.productService = productService;
    }

    @Async("taskExecutor")
    public CompletableFuture<ProductResponse> findById(Long id) {
        log.info("ASYNC START - productId={}, thread={}", id, Thread.currentThread().getName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        }

        ProductResponse response = productService.findById(id);

        log.info("ASYNC END - productId={}, thread={}", id, Thread.currentThread().getName());

        return CompletableFuture.completedFuture(response);
    }
}
