package com.centegy.ecommerce.order_service.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotEmpty(message = "Order must contain at least one item")
    @Valid          //this checks the rules inside OrderItemsRequestDto
    private List<OrderItemsRequestDto> orderItems;

}
