package com.amazonai.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.amazonai.backend.entity.Product;
import com.amazonai.backend.service.RecommendationService;


@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "http://localhost:5173")
public class RecommendationController {


    private final RecommendationService recommendationService;


    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    // Personalized recommendations
    @GetMapping("/{userId}")
    public List<Product> getRecommendations(
            @PathVariable Long userId) {

        return recommendationService.getRecommendations(userId);
    }
}