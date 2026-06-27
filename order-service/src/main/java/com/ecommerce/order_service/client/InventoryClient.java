package com.ecommerce.order_service.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class InventoryClient {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackDeductStock")
    public void deductStock(Object inventoryRequestPayload) {
        String inventoryUrl = "http://inventory-service/api/inventory/deduct-stock";
        restTemplate.put(inventoryUrl, inventoryRequestPayload);
    }

    public void fallbackDeductStock(Object inventoryRequestPayload, Throwable throwable) {
            throw new RuntimeException("Inventory Service is currently unavailable. Please try again later.");
    }

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackRestoreStock")
    public void restoreStock(Object inventoryRequestPayload) {
        String inventoryUrl = "http://inventory-service/api/inventory/restore-stock";
        restTemplate.put(inventoryUrl, inventoryRequestPayload);
    }

    public void fallbackRestoreStock(Object inventoryRequestPayload, Throwable throwable) {
        throw new RuntimeException("Inventory Service is currently unavailable. Please try again later.");
    }

}
