package com.counterly.gateway.config;

import com.counterly.gateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route("user-service", r -> r.path("/api/v1/security/**")
                        .uri("lb://user-service"))
                .route("product-service", r -> r.path("/api/v1/category/**", "/api/v1/product/**", "/api/v1/product-variant/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://product-service"))
//
//                .route("notification-service", r -> r.path("/v1/notification/**")
//                        .filters(f -> f.filter(filter))
//                        .uri("lb://notification-service"))

                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://auth-service"))

//                .route("file-storage", r -> r.path("/v1/file-storage/**")
//                        .filters(f -> f.filter(filter))
//                        .uri("lb://file-storage"))
                .build();
    }
}