package com.app.audienceize.helper;

import com.app.audienceize.entities.User;
import com.app.audienceize.services.impl.JwtAuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenGenerator {
    @Autowired
    private JwtAuthService authService;

    private final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public String generateToken(User user) {
        String token = "";
        token = Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getEmailId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
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

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
