package com.smartgrocery.repository;

import com.smartgrocery.model.Cart;
import com.smartgrocery.model.CartItem;
import com.smartgrocery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    void deleteByCartId(Long cartId);
}
