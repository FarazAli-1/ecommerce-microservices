package com.centegy.ecommerce.inventory_service.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {

    private Long id;

    private Integer stockQuantity;

    private Long productId;

}
