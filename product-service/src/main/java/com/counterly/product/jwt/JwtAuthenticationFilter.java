package com.counterly.product.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");

            if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {

                Claims claims = jwtUtil.getClaims(token.substring(7));

                List<?> rawRoles = claims.get("roles", List.class);
                List<String> roles = rawRoles.stream()
                        .map(Object::toString)
                        .toList();
                List<?> rawPermissions = claims.get("permissions", List.class);
                List<String> permissions = rawPermissions.stream()
                        .map(Object::toString)
                        .toList();

                // Convert both roles + permissions to GrantedAuthority
                Collection<GrantedAuthority> authorities = new ArrayList<>();

                authorities.addAll(roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());

                authorities.addAll(permissions.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        authorities
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}