package com.centegy.ecommerce.inventory_service.controller;

import com.centegy.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.centegy.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.centegy.ecommerce.inventory_service.repository.InventoryRepository;
import com.centegy.ecommerce.inventory_service.service.InventoryService;
import com.centegy.ecommerce_shared.dto.ApiResponse;
import com.centegy.ecommerce_shared.dto.PageResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse> addInventory(@Valid @RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.createInventory(inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Inventory added successfully", inventoryResponseDto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateInventory(@PathVariable Long id,@Valid @RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.updateInventory(id, inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory updated successfully", inventoryResponseDto));
    }

    @PutMapping("/add-stock")
    public ResponseEntity<ApiResponse> addStock(@Valid @RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.addStock(inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory added successfully", inventoryResponseDto));
    }

    @PutMapping("/deduct-stock")
    public ResponseEntity<ApiResponse> deductStock(@Valid @RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.deductStock(inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory deducted successfully", inventoryResponseDto));
    }

    @PutMapping("/restore-stock")
    public ResponseEntity<ApiResponse> restoreStock(@Valid @RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.restoreStock(inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory restored successfully", inventoryResponseDto));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getInventoryById(@PathVariable Long id){
        InventoryResponseDto inventoryResponseDto = inventoryService.findInventoryById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Inventory found successfully", inventoryResponseDto));

    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllInventory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<InventoryResponseDto> inventoryResponseDto = inventoryService.findAllInventory(page, size);
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
