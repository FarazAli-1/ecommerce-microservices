package com.centegy.ecommerce.inventory_service.service;


import com.centegy.ecommerce.inventory_service.client.ProductClient;
import com.centegy.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.centegy.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.centegy.ecommerce.inventory_service.mapper.InventoryMapper;
import com.centegy.ecommerce.inventory_service.model.Inventory;
import com.centegy.ecommerce.inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Transactional
@Service
public class InventoryServiceImpl implements  InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ProductClient  productClient;


    @Override
    public InventoryResponseDto createInventory(InventoryRequestDto inventoryRequestDto) {

     if(inventoryRepository.findByProductId(inventoryRequestDto.getProductId()).isPresent()){
         throw new RuntimeException("Inventory for product with id " + inventoryRequestDto.getProductId() + " already exists");
     }

     try {
         productClient.verifyProduct(inventoryRequestDto.getProductId());
     } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException(e.getMessage());
     }

     Inventory inventory = inventoryMapper.mapToInventory(inventoryRequestDto);

     return inventoryMapper.mapToInventoryResponseDto(inventoryRepository.save(inventory));

    }

    @Override
    public InventoryResponseDto updateInventory(Long id, InventoryRequestDto inventoryRequestDto) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " +id));


        inventory.setStockQuantity(inventoryRequestDto.getStockQuantity());
        inventory.setProductId(inventoryRequestDto.getProductId());

        return inventoryMapper.mapToInventoryResponseDto(inventoryRepository.save(inventory));

    }

    @Override
    public void deleteInventory(Long inventoryId) {

        if (!inventoryRepository.existsById(inventoryId)) {
            throw new RuntimeException("Inventory not found with id: " + inventoryId);
        }
        inventoryRepository.deleteById(inventoryId);

    }

    @Override
    public InventoryResponseDto findInventoryById(Long inventoryId) {

        return inventoryRepository.findById(inventoryId).map(inventoryMapper::mapToInventoryResponseDto)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + inventoryId));

    }

    @Override
    public List<InventoryResponseDto> findAllInventory() {
        return inventoryRepository.findAll().stream().map(inventoryMapper :: mapToInventoryResponseDto).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public InventoryResponseDto addStock(InventoryRequestDto inventoryRequestDto) {

        Inventory inventory = inventoryRepository.findByProductId(inventoryRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + inventoryRequestDto.getProductId()));


        int newStockQuantity = inventory.getStockQuantity() + inventoryRequestDto.getStockQuantity();

        inventory.setStockQuantity(newStockQuantity);

        return inventoryMapper.mapToInventoryResponseDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseDto deductStock(InventoryRequestDto inventoryRequestDto) {

        Inventory inventory = inventoryRepository.findByProductId(inventoryRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + inventoryRequestDto.getProductId()));


        int newStockQuantity = inventory.getStockQuantity() - inventoryRequestDto.getStockQuantity();

        if (newStockQuantity < 0) {
            throw new RuntimeException("Stock quantity cannot be less than 0");
        }

        inventory.setStockQuantity(newStockQuantity);


        try {
            return inventoryMapper.mapToInventoryResponseDto(inventoryRepository.save(inventory));
        } catch (org.springframework.orm.ObjectOptimisticLockingFailureException e) {
            e.printStackTrace();
            throw new RuntimeException("Sorry, someone else just purchased this item.");
        }

    }

    @Override
    public InventoryResponseDto restoreStock(InventoryRequestDto inventoryRequestDto) {

        Inventory inventory = inventoryRepository.findByProductId(inventoryRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + inventoryRequestDto.getProductId()));

        int newStockQuantity = inventory.getStockQuantity() + inventoryRequestDto.getStockQuantity();

       inventory.setStockQuantity(newStockQuantity);

        return inventoryMapper.mapToInventoryResponseDto(inventoryRepository.save(inventory));
    }
}
