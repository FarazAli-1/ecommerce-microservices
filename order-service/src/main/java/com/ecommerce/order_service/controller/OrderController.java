package com.ecommerce.order_service.controller;


import com.centegy.ecommerce_shared.dto.ApiResponse;
import com.ecommerce.order_service.dto.request.OrderRequestDto;
import com.ecommerce.order_service.dto.response.OrderResponseDto;
import com.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        OrderResponseDto orderResponseDto = orderService.placeOrder(orderRequestDto);
        return ResponseEntity.ok(new ApiResponse(true, "Order created successfully", orderResponseDto));
    }


}
