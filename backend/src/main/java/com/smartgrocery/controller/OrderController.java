package com.smartgrocery.controller;

import com.smartgrocery.dto.CheckoutRequest;
import com.smartgrocery.dto.OrderDto;
import com.smartgrocery.security.UserPrincipal;
import com.smartgrocery.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout(@AuthenticationPrincipal UserPrincipal user,
                                            @Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(orderService.checkout(user.getUserId(), request));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getMyOrders(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(orderService.getOrdersByUser(user.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@AuthenticationPrincipal UserPrincipal user,
                                                 @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id, user.getUserId()));
    }
}
