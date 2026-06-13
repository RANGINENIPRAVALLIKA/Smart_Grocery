package com.smartgrocery.controller;

import com.smartgrocery.dto.WishlistItemDto;
import com.smartgrocery.security.UserPrincipal;
import com.smartgrocery.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<List<WishlistItemDto>> getWishlist(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(wishlistService.getWishlist(user.getUserId()));
    }

    @PostMapping("/items")
    public ResponseEntity<List<WishlistItemDto>> addToWishlist(@AuthenticationPrincipal UserPrincipal user,
                                                              @RequestParam Long productId) {
        return ResponseEntity.ok(wishlistService.addToWishlist(user.getUserId(), productId));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<List<WishlistItemDto>> removeFromWishlist(@AuthenticationPrincipal UserPrincipal user,
                                                                    @PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.removeFromWishlist(user.getUserId(), productId));
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<Boolean> isInWishlist(@AuthenticationPrincipal UserPrincipal user,
                                                @PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.isInWishlist(user.getUserId(), productId));
    }
}
