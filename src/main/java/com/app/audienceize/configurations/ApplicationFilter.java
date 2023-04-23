package com.app.audienceize.configurations;

import com.app.audienceize.entities.User;
import com.app.audienceize.helper.JwtTokenGenerator;
import com.app.audienceize.services.impl.JwtAuthService;
import com.app.audienceize.services.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApplicationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtAuthService authService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtTokenGenerator generator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String JWT_TOKEN = authHeader.substring(7);

        String username = authService.extractUsername(JWT_TOKEN);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User currentUser = (User) userService.loadUserByUsername(username);
            boolean isTokenValid = generator.validateJwtToken(JWT_TOKEN, currentUser);
            if (isTokenValid) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(currentUser,
                        null, currentUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            doFilter(request, response, filterChain);
        }
    }
}
