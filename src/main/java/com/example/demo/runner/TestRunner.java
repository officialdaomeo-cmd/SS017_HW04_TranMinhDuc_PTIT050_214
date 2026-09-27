package com.example.demo.runner;

import com.example.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class TestRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting concurrent test...");
        int numberOfRequests = 50;
        String testProductId = "PROD-123";

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfRequests; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                String result = productService.getProductInfo(testProductId);
                logger.info("Thread {} received: {}", Thread.currentThread().getName(), result);
            });
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        long endTime = System.currentTimeMillis();
        logger.info("All requests completed in {} ms", (endTime - startTime));
        logger.info("Test finished.");
        System.exit(0); // Exit after testing
    }
}
