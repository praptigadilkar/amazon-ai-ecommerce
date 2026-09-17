package com.amazonai.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonai.backend.dto.CartResponse;
import com.amazonai.backend.entity.Cart;
import com.amazonai.backend.entity.Product;
import com.amazonai.backend.repository.CartRepository;
import com.amazonai.backend.repository.ProductRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart addToCart(Cart cart) {

        if (cart.getProductId() <= 0) {
            throw new IllegalArgumentException("Product ID is required");
        }

        Cart existingCart = cartRepository.findByUserIdAndProductId(
                cart.getUserId(),
                cart.getProductId()
        );

        if (existingCart != null) {

            existingCart.setQuantity(
                    existingCart.getQuantity() + cart.getQuantity()
            );

            return cartRepository.save(existingCart);

        } else {

            return cartRepository.save(cart);
        }
    }

    public List<CartResponse> getCartDetails(Long userId) {

        List<Cart> carts = cartRepository.findByUserId(userId);

        List<CartResponse> responseList = new ArrayList<>();

        for (Cart cart : carts) {

            if (cart.getProductId() <= 0) {
                continue;
            }

            CartResponse response = new CartResponse();

            response.setCartId(cart.getId());
            response.setUserId(cart.getUserId());
            response.setQuantity(cart.getQuantity());

            Product product = productRepository
                    .findById(cart.getProductId())
                    .orElse(null);

            if (product != null) {

                response.setProductName(product.getName());
                response.setProductPrice(product.getPrice());

                response.setTotalPrice(
                        product.getPrice() * cart.getQuantity()
                );
            }

            responseList.add(response);
        }

        return responseList;
    }

    public void removeFromCart(Long id) {
        cartRepository.deleteById(id);
    }
}
