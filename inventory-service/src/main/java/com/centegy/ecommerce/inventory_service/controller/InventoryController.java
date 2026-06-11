package com.centegy.ecommerce.inventory_service.controller;

import com.centegy.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.centegy.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.centegy.ecommerce.inventory_service.model.Inventory;
import com.centegy.ecommerce.inventory_service.repository.InventoryRepository;
import com.centegy.ecommerce.inventory_service.service.InventoryService;
import com.centegy.ecommerce_shared.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;


    @PostMapping
    public ResponseEntity<ApiResponse> addInventory(@RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.createInventory(inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Inventory added successfully", inventoryResponseDto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateInventory(@PathVariable Long id, @RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.updateInventory(id, inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory updated successfully", inventoryResponseDto));
    }

    @PutMapping("/add-stock")
    public ResponseEntity<ApiResponse> addStock(@RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.addStock(inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory added successfully", inventoryResponseDto));
    }

    @PutMapping("/deduct-stock")
    public ResponseEntity<ApiResponse> deductStock(@RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.deductStock(inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory deducted successfully", inventoryResponseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getInventoryById(@PathVariable Long id){
        InventoryResponseDto inventoryResponseDto = inventoryService.findInventoryById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory found successfully", inventoryResponseDto));

    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllInventory(){
        List<InventoryResponseDto> inventoryResponseDto = inventoryService.findAllInventory();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory found successfully", inventoryResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInventoryById(@PathVariable Long id){
        inventoryService.deleteInventory(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory deleted successfully", id));
    }


}
