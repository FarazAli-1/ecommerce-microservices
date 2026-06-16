package com.ecommerce.order_service.service;


import com.centegy.ecommerce_shared.dto.ApiResponse;
import com.ecommerce.order_service.dto.request.OrderRequestDto;
import com.ecommerce.order_service.dto.response.OrderResponseDto;
import com.ecommerce.order_service.mapper.OrderMapper;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderItems;
import com.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate;

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

        for(OrderItems item : order.getOrderItems()) {

            String productUrl = "http://product-service/api/product/" + item.getProductId();

            try {
                restTemplate.getForEntity(productUrl, ApiResponse.class);
            }
            catch (Exception e) {
                throw new RuntimeException("Product not found");
            }

            String inventoryUrl = "http://inventory-service/api/inventory/deduct-stock";

            InventoryRequestPayload inventoryRequestPayload = new InventoryRequestPayload(item.getQuantity(), item.getProductId());

            try {
                restTemplate.put(inventoryUrl, inventoryRequestPayload);
            }
            catch (Exception e) {
                throw new RuntimeException("Transaction Failed: Not enough stock for Product ID " + item.getProductId());            }

        }

        orderRepository.save(order);
        return orderMapper.mapToOrderResponseDto(order);

    }
    record InventoryRequestPayload(Integer stockQuantity, Long productId) {}
}
