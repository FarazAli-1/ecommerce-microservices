package com.centegy.ecommerce.order_service.service;

import com.centegy.ecommerce_shared.dto.PageResponse;
import com.centegy.ecommerce.order_service.dto.request.OrderRequestDto;
import com.centegy.ecommerce.order_service.dto.response.OrderResponseDto;

public interface OrderService {

    OrderResponseDto getOrderById(Long orderId);

    PageResponse<OrderResponseDto> getAllOrders(int page, int size);

    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);

}
