package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.request.OrderRequestDto;
import com.ecommerce.order_service.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {

    public OrderResponseDto getOrderById(Long orderId);

    public List<OrderResponseDto> getAllOrders();

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);

}
