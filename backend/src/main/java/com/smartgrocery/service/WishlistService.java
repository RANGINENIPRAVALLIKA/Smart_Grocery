package com.smartgrocery.service;

import com.smartgrocery.dto.WishlistItemDto;
import com.smartgrocery.model.Product;
import com.smartgrocery.model.User;
import com.smartgrocery.model.Wishlist;
import com.smartgrocery.model.WishlistItem;
import com.smartgrocery.repository.ProductRepository;
import com.smartgrocery.repository.UserRepository;
import com.smartgrocery.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<WishlistItemDto> getWishlist(Long userId) {
        Wishlist wishlist = getOrCreateWishlist(userId);
        return wishlist.getItems().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public List<WishlistItemDto> addToWishlist(Long userId, Long productId) {
        Wishlist wishlist = getOrCreateWishlist(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        boolean exists = wishlist.getItems().stream()
                .anyMatch(i -> i.getProduct().getId().equals(productId));
        if (!exists) {
            WishlistItem item = WishlistItem.builder().wishlist(wishlist).product(product).build();
            wishlist.getItems().add(item);
            wishlistRepository.save(wishlist);
        }
        return getWishlist(userId);
    }

    @Transactional
    public List<WishlistItemDto> removeFromWishlist(Long userId, Long productId) {
        Wishlist wishlist = getOrCreateWishlist(userId);
        wishlist.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        wishlistRepository.save(wishlist);
        return getWishlist(userId);
    }

    public boolean isInWishlist(Long userId, Long productId) {
        Wishlist wishlist = getOrCreateWishlist(userId);
        return wishlist.getItems().stream().anyMatch(i -> i.getProduct().getId().equals(productId));
    }

    private Wishlist getOrCreateWishlist(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return wishlistRepository.findByUser(user).orElseGet(() -> {
            Wishlist w = Wishlist.builder().user(user).build();
            return wishlistRepository.save(w);
        });
    }

    private WishlistItemDto toDto(WishlistItem i) {
        return WishlistItemDto.builder()
                .id(i.getId())
                .productId(i.getProduct().getId())
                .productName(i.getProduct().getName())
                .imageUrl(i.getProduct().getImageUrl())
                .price(i.getProduct().getPrice())
                .category(i.getProduct().getCategory())
                .stock(i.getProduct().getStock())
                .build();
    }
}
