package com.rhumuda.charterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.rhumuda.charterservice.model")
@EnableJpaRepositories("com.rhumuda.charterservice.repository")
public class CharterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CharterServiceApplication.class, args);
    }
} 