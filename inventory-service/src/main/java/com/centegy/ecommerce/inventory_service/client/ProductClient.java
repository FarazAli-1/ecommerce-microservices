package com.centegy.ecommerce.inventory_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "product", fallbackMethod = "fallbackVerifyProduct")
    public void verifyProduct(Long productId){

        String productUrl = "http://product-service/api/product/" + productId;
        restTemplate.getForEntity(productUrl, Object.class);


    }

    public void fallbackVerifyProduct(Long productId, Throwable throwable){
        throw new RuntimeException("Product Service is currently unavailable. Please try again later.");
    }






}
