package com.juanroy.secondbrain.auth.service;

import com.juanroy.secondbrain.auth.model.UserCredential;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtTokenService {
    @Value("${security.jwt.secret-key:secondbrain-secret-key-change-me-at-least-32-bytes}")
    private String secretKey;

    @Value("${security.jwt.access-token-expiration-seconds:3600}")
    private long accessTokenExpirationSeconds;

    public String generateToken(UserCredential userCredential) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userCredential.getUsername())
                .claim("uid", userCredential.getId())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTokenExpirationSeconds)))
                .signWith(getSignInKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String expectedUsername) {
        try {
            String username = extractUsername(token);
            Date expiration = extractAllClaims(token).getExpiration();
            return expectedUsername.equals(username) && expiration.after(new Date());
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationSeconds;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
