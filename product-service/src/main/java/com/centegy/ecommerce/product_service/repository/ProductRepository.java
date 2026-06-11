package com.centegy.ecommerce.product_service.repository;

import com.centegy.ecommerce.product_service.model.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @NonNull
    Optional<Product> findById(@NonNull Long id);

    List<Product> findByCategoryId(Long categoryId);

}
