package com.centegy.ecommerce.product_service.mapper;


import com.centegy.ecommerce.product_service.dto.request.ProductRequestDto;
import com.centegy.ecommerce.product_service.dto.response.ProductResponseDto;
import com.centegy.ecommerce.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductResponseDto mapToProductResponseDto(Product product);

    @Mapping(target = "category", ignore = true)
    Product mapToProductEntity(ProductRequestDto productResponseDto);


}
