package com.amazonai.backend.service;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String getRecommendation(String prompt) {


        String geminiUrl =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite:generateContent?key="
        + apiKey;


        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(requestBody, headers);



        try {

            String response = restTemplate.postForObject(
        geminiUrl,
        request,
        String.class
);

JsonNode root = objectMapper.readTree(response);

String aiText = root
    .get("candidates")
    .get(0)
    .get("content")
    .get("parts")
    .get(0)
    .get("text")
    .asText();
return aiText;


        } catch (Exception e) {

            e.printStackTrace();
            return "Gemini Error: " + e.getMessage();

        }

    }

}