package com.owner_service.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            // token : Here we are removing the Bearer so check the token
            String token = header.substring(7);

            // Checking whether the token is expired or not
            if (!jwtUtil.isTokenExpired(token)) {

                String email = jwtUtil.extractEmail(token);
                UUID userId = jwtUtil.extractUserId(token);
                String role = jwtUtil.extractRole(token);

                // ye Spring Security ka standard object hai jo batata hai
                //"ye user authenticated hai"
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                request.setAttribute("userId", userId);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        //Request moves forward
        filterChain.doFilter(request, response);
    }
}
