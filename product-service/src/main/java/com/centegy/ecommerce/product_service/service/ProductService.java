package com.centegy.ecommerce.product_service.service;

import com.centegy.ecommerce.product_service.dto.request.ProductRequestDto;
import com.centegy.ecommerce.product_service.dto.response.ProductResponseDto;

import com.centegy.ecommerce.product_service.model.Product;
import com.centegy.ecommerce_shared.dto.PageResponse;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto);

    void deleteProduct(Long productId);

    List<ProductResponseDto> findByCategory(Long categoryId);

    PageResponse<ProductResponseDto> findAllProducts(int page, int size);

    ProductResponseDto findProductById(Long id);



}
