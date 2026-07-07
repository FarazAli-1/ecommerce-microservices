package com.centegy.ecommerce.order_service.service;


import com.centegy.ecommerce_shared.dto.ApiResponse;
import com.centegy.ecommerce_shared.dto.PageResponse;
import com.centegy.ecommerce.order_service.client.InventoryClient;
import com.centegy.ecommerce.order_service.dto.external.ProductResponseDto;
import com.centegy.ecommerce.order_service.dto.request.OrderRequestDto;
import com.centegy.ecommerce.order_service.dto.response.OrderResponseDto;
import com.centegy.ecommerce.order_service.mapper.OrderMapper;
import com.centegy.ecommerce.order_service.model.Order;
import com.centegy.ecommerce.order_service.model.OrderItems;
import com.centegy.ecommerce.order_service.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
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
    public PageResponse<OrderResponseDto> getAllOrders(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> pagedData = orderRepository.findAll(pageable);

        List<OrderResponseDto> content = pagedData.getContent().stream()
                .map(orderMapper::mapToOrderResponseDto)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                pagedData.getNumber(),
                pagedData.getSize(),
                pagedData.getTotalPages(),
                pagedData.getTotalElements(),
                pagedData.getNumberOfElements(),
                pagedData.isFirst(),
                pagedData.isLast(),
                pagedData.hasNext(),
                pagedData.hasPrevious()
        );

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

            inventoryClient.deductStock(inventoryRequestPayload);


            item.setPrice(product.getPrice());
        }

        try {
            orderRepository.save(order);
        }catch (Exception databaseException) {

            System.err.println("Failed to save order. Starting rollback." );

            for (OrderItems item : order.getOrderItems()) {

                InventoryRequestPayload inventoryRequestPayload = new InventoryRequestPayload(item.getQuantity(), item.getProductId());

                try {
                    inventoryClient.restoreStock(inventoryRequestPayload);
                    System.out.println("Successfully restored inventory for " + item.getProductId());
                } catch (Exception RollBackException) {
                    System.err.println("Failed to restore inventory for " + item.getProductId());

                }

            }
            throw  new RuntimeException("Order Failed. No stock deducted");

        }

        return orderMapper.mapToOrderResponseDto(order);

    }


    record InventoryRequestPayload(Integer stockQuantity, Long productId) {}
}
