package com.centegy.ecommerce.inventory_service.service;

import com.centegy.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.centegy.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.centegy.ecommerce.inventory_service.model.Inventory;

import java.util.List;

public interface InventoryService {

    InventoryResponseDto createInventory(InventoryRequestDto inventoryRequestDto);

    InventoryResponseDto updateInventory(Long id, InventoryRequestDto inventoryRequestDto);

    void deleteInventory(Long inventoryId);

    InventoryResponseDto findInventoryById(Long inventoryId);

    List<InventoryResponseDto> findAllInventory();

    InventoryResponseDto addStock(InventoryRequestDto inventoryRequestDto);
    InventoryResponseDto deductStock(InventoryRequestDto inventoryRequestDto);
    InventoryResponseDto restoreStock(InventoryRequestDto inventoryRequestDto);

}
