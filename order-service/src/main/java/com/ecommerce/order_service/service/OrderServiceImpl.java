package com.ecommerce.order_service.service;


import com.centegy.ecommerce_shared.dto.ApiResponse;
import com.ecommerce.order_service.client.InventoryClient;
import com.ecommerce.order_service.dto.external.ProductResponseDto;
import com.ecommerce.order_service.dto.request.OrderRequestDto;
import com.ecommerce.order_service.dto.response.OrderResponseDto;
import com.ecommerce.order_service.mapper.OrderMapper;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderItems;
import com.ecommerce.order_service.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final InventoryClient  inventoryClient;

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(orderMapper::mapToOrderResponseDto)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));

    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::mapToOrderResponseDto).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {

        Order order = orderMapper.mapToOrderEntity(orderRequestDto);
        ProductResponseDto product;

        for(OrderItems item : order.getOrderItems()) {

            String productUrl = "http://product-service/api/product/" + item.getProductId();

            try {
                ResponseEntity<ApiResponse> response = restTemplate.getForEntity(productUrl, ApiResponse.class);
                Object rawData = response.getBody().getData();
                product = objectMapper.convertValue(rawData, ProductResponseDto.class);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to fetch product details: " + e.getMessage());
            }

            InventoryRequestPayload inventoryRequestPayload = new InventoryRequestPayload(item.getQuantity(), item.getProductId());

            try {
                inventoryClient.deductStock(inventoryRequestPayload);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }

            item.setPrice(product.getPrice());
        }



        orderRepository.save(order);
        return orderMapper.mapToOrderResponseDto(order);

    }
    record InventoryRequestPayload(Integer stockQuantity, Long productId) {}
}
