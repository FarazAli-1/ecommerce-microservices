package com.centegy.ecommerce.product_service.service;

import com.centegy.ecommerce.product_service.dto.request.ProductRequestDto;
import com.centegy.ecommerce.product_service.dto.response.ProductResponseDto;
import com.centegy.ecommerce.product_service.mapper.ProductMapper;
import com.centegy.ecommerce.product_service.model.Category;
import com.centegy.ecommerce.product_service.model.Product;
import com.centegy.ecommerce.product_service.repository.CategoryRepository;
import com.centegy.ecommerce.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {

        Category category = categoryRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id " + productRequestDto.getCategoryId()));

        Product product = productMapper.mapToProductEntity(productRequestDto);

        product.setCategory(category);

        return productMapper.mapToProductResponseDto(productRepository.save(product));

    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());

        return productMapper.mapToProductResponseDto(productRepository.save(product));


    }

    @Override
    public void deleteProduct(Long productId) {

        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with id: " + productId);
        }

        productRepository.deleteById(productId);

    }

    @Override
    public List<ProductResponseDto> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream().map(productMapper::mapToProductResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> findAllProducts() {
        return productRepository.findAll().stream().map(productMapper::mapToProductResponseDto).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public ProductResponseDto findProductById(Long id) {
        return productRepository.findById(id).map(productMapper::mapToProductResponseDto)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}
