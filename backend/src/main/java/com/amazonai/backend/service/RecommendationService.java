package com.amazonai.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonai.backend.entity.Order;
import com.amazonai.backend.entity.Product;
import com.amazonai.backend.repository.OrderRepository;
import com.amazonai.backend.repository.ProductRepository;


@Service
public class RecommendationService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    public RecommendationService(ProductRepository productRepository,
                                 OrderRepository orderRepository) {

        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }


    // AI recommendation based on user's previous orders
    public List<Product> getRecommendations(Long userId) {


        List<Order> orders = orderRepository.findByUserId(userId);


        if (orders.isEmpty()) {
            return productRepository.findAll();
        }


        Order lastOrder = orders.get(orders.size() - 1);


        Product purchasedProduct = productRepository
                .findById(lastOrder.getProductId())
                .orElse(null);


        if (purchasedProduct == null) {
            return productRepository.findAll();
        }


        return productRepository
                .findByCategory(purchasedProduct.getCategory());
    }
}