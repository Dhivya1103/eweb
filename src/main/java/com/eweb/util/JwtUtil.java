package com.eweb.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	 @Value("${jwt.secret}")
	    private String jwtSecret;

	    @Value("${jwt.expiration}")
	    private int jwtExpirationMs;

	    private final Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String generateToken(String email, boolean rememberMe) {
        long expiration = rememberMe ? 2592000000L : jwtExpirationMs; // 30 days or 1 hour in ms

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(jwtSecretKey)
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

	public String generateToken(String mobile) {
		 return Jwts.builder()
	                .setSubject(mobile)
	                .setIssuedAt(new Date())
	                .setExpiration(
	                        new Date(
	                                System.currentTimeMillis()
	                                        + jwtExpirationMs
	                        )
	                )
	                .signWith(
	                        SignatureAlgorithm.HS256,
	                        jwtSecretKey
	                )
	                .compact();
	    }
}
