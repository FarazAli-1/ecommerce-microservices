package com.centegy.ecommerce.inventory_service.service;

import com.centegy.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.centegy.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.centegy.ecommerce.inventory_service.model.Inventory;
import com.centegy.ecommerce_shared.dto.PageResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponseDto createInventory(InventoryRequestDto inventoryRequestDto);

    InventoryResponseDto updateInventory(Long id, InventoryRequestDto inventoryRequestDto);

    void deleteInventory(Long inventoryId);

    InventoryResponseDto findInventoryById(Long inventoryId);

    PageResponse<InventoryResponseDto> findAllInventory(int page, int size);

    InventoryResponseDto addStock(InventoryRequestDto inventoryRequestDto);
    InventoryResponseDto deductStock(InventoryRequestDto inventoryRequestDto);
    InventoryResponseDto restoreStock(InventoryRequestDto inventoryRequestDto);

}
