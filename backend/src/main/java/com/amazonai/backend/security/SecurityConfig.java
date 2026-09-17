package com.amazonai.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;


    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {


        http
            .csrf(csrf -> csrf.disable())


            .cors(cors -> cors.configurationSource(request -> {

                CorsConfiguration config = new CorsConfiguration();

                config.addAllowedOrigin("http://localhost:5173");
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");

                return config;

            }))


            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )


            .authorizeHttpRequests(auth -> auth

               .requestMatchers(
    "/api/auth/**",
    "/api/products/**",
    "/api/ai/**",
    "/api/cart/**",
    "/api/orders/**",
    "/error"
)
.permitAll()


                .anyRequest()
                .authenticated()

            )


            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );


        return http.build();

    }

}