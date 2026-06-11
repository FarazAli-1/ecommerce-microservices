package com.centegy.ecommerce.inventory_service.mapper;

import com.centegy.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.centegy.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.centegy.ecommerce.inventory_service.model.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory mapToInventory(InventoryRequestDto inventoryRequestDto);

    InventoryResponseDto mapToInventoryResponseDto(Inventory inventory);

}
