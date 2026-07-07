package com.centegy.ecommerce.order_service.controller;


import com.centegy.ecommerce_shared.dto.ApiResponse;
import com.centegy.ecommerce_shared.dto.PageResponse;
import com.centegy.ecommerce.order_service.dto.request.OrderRequestDto;
import com.centegy.ecommerce.order_service.dto.response.OrderResponseDto;
import com.centegy.ecommerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto){
        OrderResponseDto orderResponseDto = orderService.placeOrder(orderRequestDto);
        return ResponseEntity.ok(new ApiResponse(true, "Order created successfully", orderResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    )
    {
        return ResponseEntity.ok(new ApiResponse(true, "All orders fetched", orderService.getAllOrders(page, size)));
    }


}
