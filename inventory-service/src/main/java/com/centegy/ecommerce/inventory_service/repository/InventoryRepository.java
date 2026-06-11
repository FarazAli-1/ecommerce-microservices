package com.centegy.ecommerce.inventory_service.repository;

import com.centegy.ecommerce.inventory_service.model.Inventory;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    @NonNull
    Optional<Inventory> findByProductId(@NonNull Long productId);

}
