package com.centegy.ecommerce.product_service.service;

import com.centegy.ecommerce.product_service.dto.request.CategoryRequestDto;
import com.centegy.ecommerce.product_service.dto.response.CategoryResponseDto;
import com.centegy.ecommerce.product_service.model.Category;
import org.aspectj.apache.bcel.classfile.Module;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto updateCategory(Long id,CategoryRequestDto categoryRequestDto);

    void deleteCategory(Long categoryId);

    List<CategoryResponseDto> findAllCategories();

    CategoryResponseDto findCategoryById(Long categoryId);

}
