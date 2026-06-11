package com.centegy.ecommerce.inventory_service.service;

import com.centegy.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.centegy.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.centegy.ecommerce.inventory_service.mapper.InventoryMapper;
import com.centegy.ecommerce.inventory_service.model.Inventory;
import com.centegy.ecommerce.inventory_service.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryMapper inventoryMapper;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory;
    private InventoryRequestDto requestDto;
    private InventoryResponseDto responseDto;

    @BeforeEach
    void setUp() {
        // Dummy Data for Product ID 10
        inventory = new Inventory(50, 10L);
        inventory.setId(1L);

        requestDto = new InventoryRequestDto(10, 10L); // Asking to add/deduct 10 items
        responseDto = new InventoryResponseDto(1L, 60, 10L); // Expected after adding 10
    }

    // --- TEST 1: Create Inventory Success ---
    @Test
    void createInventory_Success() {
        // Arrange: Database says this product has no inventory yet
        when(inventoryRepository.findByProductId(10L)).thenReturn(Optional.empty());
        when(inventoryMapper.mapToInventory(requestDto)).thenReturn(inventory);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        when(inventoryMapper.mapToInventoryResponseDto(inventory)).thenReturn(responseDto);

        // Act
        InventoryResponseDto result = inventoryService.createInventory(requestDto);

        // Assert
        assertNotNull(result);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    // --- TEST 2: Create Inventory Fails (Already Exists) ---
    @Test
    void createInventory_AlreadyExists_ThrowsException() {
        // Arrange: Database says inventory already exists
        when(inventoryRepository.findByProductId(10L)).thenReturn(Optional.of(inventory));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            inventoryService.createInventory(requestDto);
        });

        assertTrue(exception.getMessage().contains("Product already exists"));
        verify(inventoryRepository, never()).save(any(Inventory.class)); // Ensures we didn't save
    }

    // --- TEST 3: Add Stock Success ---
    @Test
    void addStock_Success() {
        // Arrange
        when(inventoryRepository.findByProductId(10L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        when(inventoryMapper.mapToInventoryResponseDto(inventory)).thenReturn(responseDto);

        // Act
        InventoryResponseDto result = inventoryService.addStock(requestDto);

        // Assert
        assertNotNull(result);
        // Verify the math happened! Original was 50, Request was 10 -> Should be 60.
        assertEquals(60, inventory.getStockQuantity());
        verify(inventoryRepository, times(1)).save(inventory);
    }

    // --- TEST 4: Deduct Stock Success ---
    @Test
    void deductStock_Success() {
        // Arrange
        when(inventoryRepository.findByProductId(10L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        InventoryResponseDto deductResponse = new InventoryResponseDto(1L, 40, 10L);
        when(inventoryMapper.mapToInventoryResponseDto(inventory)).thenReturn(deductResponse);

        // Act
        InventoryResponseDto result = inventoryService.deductStock(requestDto);

        // Assert
        assertNotNull(result);
        // Verify the math happened! Original was 50, Request was 10 -> Should be 40.
        assertEquals(40, inventory.getStockQuantity());
        verify(inventoryRepository, times(1)).save(inventory);
    }

    // --- TEST 5: Deduct Stock Fails (Insufficient Stock) ---
    @Test
    void deductStock_InsufficientStock_ThrowsException() {
        // Arrange: Change the request to ask for 100 items (we only have 50)
        InventoryRequestDto largeRequest = new InventoryRequestDto(100, 10L);
        when(inventoryRepository.findByProductId(10L)).thenReturn(Optional.of(inventory));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            inventoryService.deductStock(largeRequest);
        });

        assertEquals("Stock quantity cannot be less than 0", exception.getMessage());
        verify(inventoryRepository, never()).save(any(Inventory.class)); // Ensures we didn't save negative stock
    }
}