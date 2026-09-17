package com.amazonai.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonai.backend.dto.OrderResponse;
import com.amazonai.backend.entity.Cart;
import com.amazonai.backend.entity.Order;
import com.amazonai.backend.entity.Product;
import com.amazonai.backend.repository.CartRepository;
import com.amazonai.backend.repository.OrderRepository;
import com.amazonai.backend.repository.ProductRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // Add order manually
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    // Get all orders with product details
    public List<OrderResponse> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order -> {

            OrderResponse response = new OrderResponse();

            response.setId(order.getId());
            response.setQuantity(order.getQuantity());
            response.setTotalPrice(order.getTotalPrice());
            response.setStatus(order.getStatus());

            Product product = productRepository
                    .findById(order.getProductId())
                    .orElse(null);

            if (product != null) {
                response.setProductName(product.getName());
            }

            return response;

        }).toList();
    }

    // Get orders of particular user
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Place order from entire cart
    @Transactional
    public List<Order> placeOrder(Long userId) {

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<Order> savedOrders = new ArrayList<>();

        for (Cart cart : cartItems) {

            Product product = productRepository
                    .findById(cart.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found")
                    );

            Order order = new Order();

            order.setUserId(userId);
            order.setProductId(cart.getProductId());
            order.setQuantity(cart.getQuantity());
            order.setStatus("PLACED");

            order.setTotalPrice(
                    product.getPrice() * cart.getQuantity()
            );

            Order savedOrder = orderRepository.save(order);

            savedOrders.add(savedOrder);
        }

        // Delete cart after all orders are created
        cartRepository.deleteByUserId(userId);

        return savedOrders;
    }

    // Update order status
    public Order updateStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found")
                );

        order.setStatus(status);

        return orderRepository.save(order);
    }
}