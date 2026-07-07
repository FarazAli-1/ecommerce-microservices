package com.centegy.ecommerce.product_service.repository;

import com.centegy.ecommerce.product_service.dto.response.ProductResponseDto;
import com.centegy.ecommerce.product_service.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryId(Long categoryId);

    @Query("Select new com.centegy.ecommerce.product_service.dto.response.ProductResponseDto"
        +  "(p.id, p.name, p.description, p.price, p.category.id)"
        +  "From Product p")
    Page<ProductResponseDto> findAllProductsAsDto(Pageable pageable);



}
