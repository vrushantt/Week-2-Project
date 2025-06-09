package com.vrushant.ratingsystemapplication.Config;

import com.vrushant.ratingsystemapplication.Model.User;
import com.vrushant.ratingsystemapplication.Repository.UserRepository;
import com.vrushant.ratingsystemapplication.Service.JwtUtil;
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
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepo;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/login") || path.equals("/register") || path.startsWith("/h2-console");
    }

    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)

            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            String email = jwtUtil.extractEmail(token);

            String role = jwtUtil.extractRole(token); // Should return "ADMIN" or "USER"

            if (email != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepo.findByEmail(email).orElse(null);
                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(

                            user,

                            null,

                            List.of(new SimpleGrantedAuthority(role))

                    );


                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("Authenticated user: " + email + " with role:" + role);

                }

            }

            filterChain.doFilter(request, response);

        }
    }

}