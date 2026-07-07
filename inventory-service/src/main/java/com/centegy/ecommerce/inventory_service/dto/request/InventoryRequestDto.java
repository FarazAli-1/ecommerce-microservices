package com.centegy.ecommerce.inventory_service.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDto {


    @NotNull(message = "Stock quantity is required")
    @Min(value = 1, message = "Stock quantity must at least be one")
    private Integer stockQuantity;

    @NotNull(message = "Product ID is missing")
    private Long productId;


}
