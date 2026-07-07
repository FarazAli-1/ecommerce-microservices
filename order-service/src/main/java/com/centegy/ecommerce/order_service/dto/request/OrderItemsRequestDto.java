package com.centegy.ecommerce.order_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsRequestDto {

    @NotNull(message = "Product ID is missing")
    private Long productId;

    @Min(value = 1, message= "Quantity must be at least one 1")
    private int quantity;

}
