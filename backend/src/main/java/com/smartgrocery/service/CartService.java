package com.smartgrocery.service;

import com.smartgrocery.dto.CartDto;
import com.smartgrocery.dto.CartItemDto;
import com.smartgrocery.model.Cart;
import com.smartgrocery.model.CartItem;
import com.smartgrocery.model.Product;
import com.smartgrocery.model.User;
import com.smartgrocery.repository.CartRepository;
import com.smartgrocery.repository.ProductRepository;
import com.smartgrocery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartDto getCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return toDto(cart);
    }

    @Transactional
    public CartDto addToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStock() == null || product.getStock() <= 0) {
            throw new RuntimeException("Product is out of stock");
        }
        CartItem existing = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        int qty = quantity != null && quantity > 0 ? quantity : 1;
        int maxQty = Math.min(qty, product.getStock());
        if (maxQty <= 0) {
            throw new RuntimeException("Product is out of stock");
        }
        if (existing != null) {
            int newQty = Math.min(existing.getQuantity() + qty, product.getStock());
            existing.setQuantity(newQty);
        } else {
            CartItem item = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(maxQty)
                    .build();
            cart.getItems().add(item);
        }
        cart = cartRepository.save(cart);
        return toDto(cart);
    }

    @Transactional
    public CartDto updateQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not in cart"));
        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(Math.min(quantity, item.getProduct().getStock()));
        }
        cart = cartRepository.save(cart);
        return toDto(cart);
    }

    @Transactional
    public CartDto removeFromCart(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        cart = cartRepository.save(cart);
        return toDto(cart);
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart c = Cart.builder().user(user).build();
            return cartRepository.save(c);
        });
    }

    private CartDto toDto(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();
        return CartDto.builder()
                .id(cart.getId())
                .items(cart.getItems().stream().map(this::toItemDto).toList())
                .totalPrice(total)
                .build();
    }

    private CartItemDto toItemDto(CartItem i) {
        return CartItemDto.builder()
                .id(i.getId())
                .productId(i.getProduct().getId())
                .productName(i.getProduct().getName())
                .imageUrl(i.getProduct().getImageUrl())
                .price(i.getProduct().getPrice())
                .quantity(i.getQuantity())
                .stock(i.getProduct().getStock())
                .build();
    }
}
