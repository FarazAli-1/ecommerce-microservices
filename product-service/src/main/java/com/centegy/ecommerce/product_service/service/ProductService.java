package com.centegy.ecommerce.product_service.service;

import com.centegy.ecommerce.product_service.dto.request.ProductRequestDto;
import com.centegy.ecommerce.product_service.dto.response.ProductResponseDto;

import com.centegy.ecommerce.product_service.model.Product;

import java.util.List;

public interface ProductService {

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto);

    public void deleteProduct(Long productId);

    public List<ProductResponseDto> findByCategory(Long categoryId);

    public List<ProductResponseDto> findAllProducts();

    public ProductResponseDto findProductById(Long id);



}
