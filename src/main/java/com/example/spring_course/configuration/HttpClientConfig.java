package com.example.spring_course.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpClientConfig {
    @Bean
    public RestClient restCLient() {
        return RestClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }
}
