package com.smartgrocery.service;

import com.smartgrocery.dto.AuthResponse;
import com.smartgrocery.dto.LoginRequest;
import com.smartgrocery.dto.RegisterRequest;
import com.smartgrocery.model.Cart;
import com.smartgrocery.model.User;
import com.smartgrocery.model.Wishlist;
import com.smartgrocery.repository.CartRepository;
import com.smartgrocery.repository.UserRepository;
import com.smartgrocery.repository.WishlistRepository;
import com.smartgrocery.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .admin(false)
                .build();
        user = userRepository.save(user);
        Cart cart = Cart.builder().user(user).build();
        Wishlist wishlist = Wishlist.builder().user(user).build();
        cart = cartRepository.save(cart);
        wishlist = wishlistRepository.save(wishlist);
        user.setCart(cart);
        user.setWishlist(wishlist);
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.isAdmin());
        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .admin(user.isAdmin())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.isAdmin());
        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .admin(user.isAdmin())
                .build();
    }
}
