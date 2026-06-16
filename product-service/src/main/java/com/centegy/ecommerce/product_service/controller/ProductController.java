package com.centegy.ecommerce.product_service.controller;


import com.centegy.ecommerce.product_service.dto.request.ProductRequestDto;
import com.centegy.ecommerce.product_service.dto.response.ProductResponseDto;
import com.centegy.ecommerce.product_service.service.ProductService;
import com.centegy.ecommerce_shared.dto.ApiResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    public final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequestDto productRequestDto){
        ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Product created successfully", productResponseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto){
        ProductResponseDto productResponseDto = productService.updateProduct(id, productRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Product updated successfully", productResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAllProducts(){
        List<ProductResponseDto> productResponseDtoList = productService.findAllProducts();
        return ResponseEntity.ok(new ApiResponse(true, "Products fetched successfully", productResponseDtoList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findProductById(@PathVariable Long id){
        ProductResponseDto productResponseDto = productService.findProductById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Product found successfully", productResponseDto));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> findAllProductsByCategoryId(@PathVariable Long categoryId){
        List<ProductResponseDto> productResponseDtoList = productService.findByCategory(categoryId);
        return ResponseEntity.ok(new ApiResponse(true, "Categories found successfully", productResponseDtoList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse(true, "Product deleted successfully", null));
    }


}
