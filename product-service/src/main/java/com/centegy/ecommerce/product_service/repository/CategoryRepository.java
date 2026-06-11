package com.centegy.ecommerce.product_service.repository;

import com.centegy.ecommerce.product_service.model.Category;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @NonNull
    Optional<Category> findByCategoryId(@NonNull Long id);

}
