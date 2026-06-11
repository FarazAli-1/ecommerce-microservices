package com.centegy.ecommerce.inventory_service.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDto {

    private Integer stockQuantity;

    private Long productId;


}
