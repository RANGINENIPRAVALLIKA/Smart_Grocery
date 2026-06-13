package com.smartgrocery.controller;

import com.smartgrocery.dto.OrderDto;
import com.smartgrocery.dto.ProductDto;
import com.smartgrocery.dto.UserDto;
import com.smartgrocery.model.Order;
import com.smartgrocery.repository.OrderRepository;
import com.smartgrocery.service.ProductService;
import com.smartgrocery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderRepository orderRepository;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.addProduct(dto));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(o -> OrderDto.builder()
                        .id(o.getId())
                        .deliveryAddress(o.getDeliveryAddress())
                        .paymentMethod(o.getPaymentMethod())
                        .status(o.getStatus())
                        .totalAmount(o.getTotalAmount())
                        .createdAt(o.getCreatedAt())
                        .items(o.getItems().stream()
                                .map(i -> com.smartgrocery.dto.OrderItemDto.builder()
                                        .productId(i.getProduct().getId())
                                        .productName(i.getProduct().getName())
                                        .imageUrl(i.getProduct().getImageUrl())
                                        .quantity(i.getQuantity())
                                        .priceAtOrder(i.getPriceAtOrder())
                                        .build())
                                .toList())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }
}
