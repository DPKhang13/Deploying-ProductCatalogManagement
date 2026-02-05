package com.example.project.service.implement;

import com.example.project.entity.Cart;
import com.example.project.entity.CartItem;
import com.example.project.repository.CartItemRepository;
import com.example.project.repository.CartRepository;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.CartService;
import com.example.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public void addToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.findById(userId).orElseThrow());
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(
                    existingItem.get().getQuantity() + quantity
            );
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(productRepository.findById(productId).orElseThrow());
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
