package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Cacheable(value = "flash-sale", key = "#id", sync = true)
    public String getProductInfo(String id) {
        logger.info("Fetching from Database for product {}", id);
        try {
            // Simulate complex database query
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Product Info for " + id;
    }
}
