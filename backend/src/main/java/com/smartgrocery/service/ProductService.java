package com.smartgrocery.service;

import com.smartgrocery.dto.ProductDto;
import com.smartgrocery.model.Product;
import com.smartgrocery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<String> getCategories() {
        return productRepository.findAll().stream()
                .map(Product::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(String query, String category) {
        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = productRepository.findByCategoryAndNameContainingIgnoreCase(category, query);
        } else {
            products = productRepository.findByNameContainingIgnoreCase(query);
        }
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        return toDto(product);
    }

    public ProductDto addProduct(ProductDto dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .imageUrl(dto.getImageUrl())
                .rating(dto.getRating() != null ? clampRating(dto.getRating()) : 4.0)
                .stock(dto.getStock() != null ? dto.getStock() : 0)
                .build();
        product = productRepository.save(product);
        return toDto(product);
    }

    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getImageUrl() != null) product.setImageUrl(dto.getImageUrl());
        if (dto.getRating() != null) product.setRating(clampRating(dto.getRating()));
        if (dto.getStock() != null) product.setStock(dto.getStock());
        product = productRepository.save(product);
        return toDto(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductDto toDto(Product p) {
        return ProductDto.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .category(p.getCategory())
                .imageUrl(p.getImageUrl())
                .rating(p.getRating())
                .stock(p.getStock())
                .build();
    }

    private double clampRating(double rating) {
        if (rating < 1.0) return 1.0;
        if (rating > 5.0) return 5.0;
        return Math.round(rating * 10.0) / 10.0;
    }
}
