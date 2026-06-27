package com.centegy.ecommerce.product_service.service;

import com.centegy.ecommerce.product_service.dto.request.CategoryRequestDto;
import com.centegy.ecommerce.product_service.dto.response.CategoryResponseDto;
import com.centegy.ecommerce.product_service.mapper.CategoryMapper;
import com.centegy.ecommerce.product_service.model.Category;
import com.centegy.ecommerce.product_service.repository.CategoryRepository;;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {

        Category category = categoryMapper.mapToCategoryEntity(categoryRequestDto);

        return categoryMapper.mapToCategoryResponseDto(categoryRepository.save(category));

    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(categoryRequestDto.getName());
        category.setDescription(categoryRequestDto.getDescription());

        return categoryMapper.mapToCategoryResponseDto(categoryRepository.save(category));



    }

    @Override
    public void deleteCategory(Long categoryId) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Cannot delete, Category not found with id " + categoryId);
        }

        categoryRepository.deleteById(categoryId);

    }

    @Override
    public List<CategoryResponseDto> findAllCategories() {

        return categoryRepository.findAll().stream().map(categoryMapper::mapToCategoryResponseDto).collect(java.util.stream.Collectors.toList());

    }

    @Override
    public CategoryResponseDto findCategoryById(Long categoryId) {
        return categoryMapper.mapToCategoryResponseDto(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId)));
    }
}
