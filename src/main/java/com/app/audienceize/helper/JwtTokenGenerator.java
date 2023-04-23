package com.app.audienceize.helper;

import com.app.audienceize.entities.User;
import com.app.audienceize.services.impl.JwtAuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenGenerator {
    @Autowired
    private JwtAuthService authService;
    private final String SECRET = "5YexcpvTr-OLRtf-zruHtfdCmHXph3qMXoBhlloX";

    public String generateToken(User user) {
        String token = "";
        token = Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getEmailId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public boolean isTokenExpired(String token){
        Date expiryDate = authService.extractClaims(token, Claims::getExpiration);
        return expiryDate.before(new Date());
    }

    public boolean validateJwtToken(String token,User user) {
        String username = authService.extractClaims(token, Claims::getSubject);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }
}
