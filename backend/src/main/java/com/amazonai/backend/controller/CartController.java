package com.amazonai.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.amazonai.backend.dto.CartResponse;
import com.amazonai.backend.entity.Cart;
import com.amazonai.backend.service.CartService;


@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {


    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    // Get cart items with product details
@GetMapping
public List<CartResponse> getCartItems(
        @RequestParam Long userId) {

    return cartService.getCartDetails(userId);
}

    // Add product to cart
    @PostMapping
    public Cart addCart(@RequestBody Cart cart) {
        return cartService.addToCart(cart);
    }


    // Delete cart item
    @DeleteMapping("/{id}")
    public String deleteCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "Removed from cart";
    }
}