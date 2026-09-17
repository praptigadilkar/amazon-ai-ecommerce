package com.amazonai.backend.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.amazonai.backend.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {


    private final JwtService jwtService;


    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {


        String path = request.getRequestURI();


        // Skip JWT check for public APIs
        if(path.startsWith("/api/products")
        || path.startsWith("/api/auth")
        || path.startsWith("/api/ai")
        || path.startsWith("/api/cart")
        || path.startsWith("/api/orders")) {

    filterChain.doFilter(request, response);
    return;
}



        String authHeader =
                request.getHeader("Authorization");



        if(authHeader != null &&
           authHeader.startsWith("Bearer ")) {


            String token =
                    authHeader.substring(7);


            String email =
                    jwtService.extractEmail(token);


            System.out.println("Logged user: " + email);

        }



        filterChain.doFilter(request,response);

    }

}