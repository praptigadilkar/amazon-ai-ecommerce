package com.amazonai.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonai.backend.entity.Product;
import com.amazonai.backend.repository.ProductRepository;
import com.amazonai.backend.service.GeminiService;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:5173")
public class AIController {

    private final ProductRepository productRepository;
    private final GeminiService geminiService;

    public AIController(ProductRepository productRepository,
                        GeminiService geminiService) {
        this.productRepository = productRepository;
        this.geminiService = geminiService;
    }

    @GetMapping("/recommend")
    public Product recommendProducts(@RequestParam String userNeed) {

        System.out.println("AI API CALLED");

        List<Product> products = productRepository.findAll();

        String productText = products.stream()
                .map(p -> "ID: " + p.getId()
                        + ", Name: " + p.getName()
                        + ", Price: ₹" + p.getPrice())
                .reduce("", (a, b) -> a + "\n" + b);

        String prompt =
                "You are an AI shopping assistant.\n\n" +
                "User needs: " + userNeed + "\n\n" +
                "Available products:\n" + productText + "\n\n" +
                "Choose the BEST matching product.\n" +
                "Return ONLY the numeric ID of the selected product.\n" +
                "Example: 2";

        String aiResponse = geminiService.getRecommendation(prompt);

        System.out.println("AI RESPONSE: " + aiResponse);

        for (Product product : products) {

            if (aiResponse.contains(String.valueOf(product.getId()))) {
                return product;
            }
        }

        return products.get(0);
    }
}