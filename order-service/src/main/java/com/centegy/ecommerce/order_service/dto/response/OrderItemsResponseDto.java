package com.centegy.ecommerce.order_service.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsResponseDto {

    private Long id;
    private Long productId;
    private BigDecimal price;
    private int quantity;

}
