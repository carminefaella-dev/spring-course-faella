package com.example.spring_course.service;

import com.example.spring_course.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductScheduler {
    private static final Logger log = LoggerFactory.getLogger(ProductScheduler.class);

    private final ProductRepository productRepository;

    public ProductScheduler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Scheduled(
            fixedRateString = "${app.scheduler.product-stats.fixed-rate:60000}",
            initialDelayString = "${app.scheduler.product-stats.initial-delay:10000}"
    )
    public void logProductStatistics() {
        long totalProducts = productRepository.count();

        log.info(
                "SCHEDULED - catalog statistics: totalProducts={}, thread={}",
                totalProducts,
                Thread.currentThread().getName()
        );
    }
}
