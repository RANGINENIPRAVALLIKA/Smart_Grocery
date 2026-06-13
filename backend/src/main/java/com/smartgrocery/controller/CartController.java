package com.smartgrocery.controller;

import com.smartgrocery.dto.CartDto;
import com.smartgrocery.security.UserPrincipal;
import com.smartgrocery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserPrincipal user) {
        Long userId = user.getUserId();
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/items")
    public ResponseEntity<CartDto> addToCart(@AuthenticationPrincipal UserPrincipal user,
                                             @RequestParam Long productId,
                                             @RequestParam(required = false, defaultValue = "1") Integer quantity) {
        return ResponseEntity.ok(cartService.addToCart(user.getUserId(), productId, quantity));
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartDto> updateQuantity(@AuthenticationPrincipal UserPrincipal user,
                                                  @PathVariable Long productId,
                                                  @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateQuantity(user.getUserId(), productId, quantity));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartDto> removeFromCart(@AuthenticationPrincipal UserPrincipal user,
                                                  @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeFromCart(user.getUserId(), productId));
    }
}
