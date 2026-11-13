package com.example.spring_course.repository.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ProductDocument {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Instant createdAt;
    private String brand;
    private Map<String, Object> technicalDetails;
}
