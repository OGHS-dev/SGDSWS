package com.oghs.sgdsws.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.application.port.out.TokenPort;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService implements TokenPort {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Override
    public String username(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public String getUsernameFromToken(String token) {
        return username(token);
    }

    @Override
    public long expirationTime() {
        return jwtExpiration;
    }

    public Long getExpirationTime() {
        return jwtExpiration;
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String issue(String username) {
        return getToken(new HashMap<>(), username, jwtExpiration);
    }

    public String getToken(UserDetails userDetails) {
        return issue(userDetails.getUsername());
    }

    private String getToken(Map<String, Object> claims, String username, Long jwtExpiration) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean valid(String token, String username) {
        final String tokenUsername = username(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return valid(token, userDetails.getUsername());
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

}
