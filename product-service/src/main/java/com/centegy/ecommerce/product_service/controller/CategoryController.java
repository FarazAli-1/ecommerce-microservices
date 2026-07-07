package com.centegy.ecommerce.product_service.controller;


import com.centegy.ecommerce.product_service.dto.request.CategoryRequestDto;
import com.centegy.ecommerce.product_service.dto.response.CategoryResponseDto;
import com.centegy.ecommerce.product_service.service.CategoryService;
import com.centegy.ecommerce_shared.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Category created successfully", categoryResponseDto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@Valid  @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(id, categoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Category updated successfully", categoryResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAllCategories() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.findAllCategories();
        return ResponseEntity.ok(new ApiResponse(true, "Categories found successfully", categoryResponseDtoList));
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse> findCategoryById(@PathVariable Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.findCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Category found successfully", categoryResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse(true, "Category deleted successfully", null));
    }

}
