package com.example.spring_course.service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        String category,
        Instant createdAt,
        String brand,
        Map<String, Object> technicalDetails
) {
}
