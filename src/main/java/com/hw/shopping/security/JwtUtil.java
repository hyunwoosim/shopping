package com.hw.shopping.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {

    static final SecretKey key =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(
            "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
        ));

    public static String createToken() {
        String jwt = Jwts.builder()
            .claim("username", "어쩌구")
            .claim("displayName", "저쩌구")
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 10000)) //유효기간 10초
            .signWith(key)
            .compact();
        return jwt;
    }

    // JWT 까주는 함수
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token).getPayload();
        return claims;
    }
}