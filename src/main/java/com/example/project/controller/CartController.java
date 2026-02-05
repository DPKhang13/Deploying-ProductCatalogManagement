package com.example.project.controller;

import com.example.project.dto.response.ApiResponse;
import com.example.project.entity.Cart;
import com.example.project.security.CustomUserDetails;
import com.example.project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ApiResponse<Void> addToCart(
            @RequestParam Long productId,
            @RequestParam int quantity,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        cartService.addToCart(user.getId(), productId, quantity);
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<Cart> getCart(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Cart cart = cartService.getCart(user.getId());
        return ApiResponse.success(cart);
    }

    @DeleteMapping("/item/{id}")
    public ApiResponse<Void> deleteItem(
            @PathVariable Long id
    ) {
        cartService.removeFromCart(id);
        return ApiResponse.success(null);
    }
}

