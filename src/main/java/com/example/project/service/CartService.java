package com.example.project.service;

import com.example.project.entity.Cart;

public interface CartService {
    void addToCart(Long userId, Long productId, int quantity);
    Cart getCart(Long userId);
    void removeFromCart(Long productId);
}
