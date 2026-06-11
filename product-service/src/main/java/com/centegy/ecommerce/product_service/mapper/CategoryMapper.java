package com.centegy.ecommerce.product_service.mapper;


import com.centegy.ecommerce.product_service.dto.request.CategoryRequestDto;
import com.centegy.ecommerce.product_service.dto.response.CategoryResponseDto;
import com.centegy.ecommerce.product_service.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


    Category mapToCategoryEntity(CategoryRequestDto categoryResponseDto);

    CategoryResponseDto mapToCategoryResponseDto(Category category);



}
