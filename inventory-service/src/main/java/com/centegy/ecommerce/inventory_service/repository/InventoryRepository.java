package com.centegy.ecommerce.inventory_service.repository;

import com.centegy.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.centegy.ecommerce.inventory_service.model.Inventory;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    @NonNull
    Optional<Inventory> findByProductId(@NonNull Long productId);

    @Query("Select new com.centegy.ecommerce.inventory_service.dto.response.InventoryResponseDto"
        + "(i.id, i.stockQuantity, i.productId)"
        + "From Inventory i")
    Page<InventoryResponseDto> findAllInventory(Pageable pageable);

}
