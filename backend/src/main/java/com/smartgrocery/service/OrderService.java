package com.smartgrocery.service;

import com.smartgrocery.dto.CartDto;
import com.smartgrocery.dto.CheckoutRequest;
import com.smartgrocery.dto.OrderDto;
import com.smartgrocery.dto.OrderItemDto;
import com.smartgrocery.model.*;
import com.smartgrocery.repository.OrderRepository;
import com.smartgrocery.repository.ProductRepository;
import com.smartgrocery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;

    @Transactional
    public OrderDto checkout(Long userId, CheckoutRequest request) {
        CartDto cart = cartService.getCart(userId);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = Order.builder()
                .user(user)
                .deliveryAddress(request.getDeliveryAddress())
                .paymentMethod(request.getPaymentMethod())
                .status("PLACED")
                .totalAmount(cart.getTotalPrice())
                .createdAt(LocalDateTime.now())
                .build();
        for (var item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
            OrderItem oi = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(item.getQuantity())
                    .priceAtOrder(item.getPrice())
                    .build();
            order.getItems().add(oi);
        }
        order = orderRepository.save(order);
        cartService.clearCart(userId);
        return toDto(order);
    }

    public List<OrderDto> getOrdersByUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        return toDto(order);
    }

    private OrderDto toDto(Order o) {
        return OrderDto.builder()
                .id(o.getId())
                .deliveryAddress(o.getDeliveryAddress())
                .paymentMethod(o.getPaymentMethod())
                .status(o.getStatus())
                .totalAmount(o.getTotalAmount())
                .createdAt(o.getCreatedAt())
                .items(o.getItems().stream().map(this::toItemDto).toList())
                .build();
    }

    private OrderItemDto toItemDto(OrderItem i) {
        return OrderItemDto.builder()
                .productId(i.getProduct().getId())
                .productName(i.getProduct().getName())
                .imageUrl(i.getProduct().getImageUrl())
                .quantity(i.getQuantity())
                .priceAtOrder(i.getPriceAtOrder())
                .build();
    }
}
