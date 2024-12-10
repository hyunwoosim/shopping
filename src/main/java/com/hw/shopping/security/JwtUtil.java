package com.hw.shopping.security;

import com.hw.shopping.service.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;

public class JwtUtil {

    static final SecretKey key =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(
            "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
        ));

    public static String createToken(Authentication auth) {

        CustomUser user = (CustomUser) auth.getPrincipal();

        String collect = auth.getAuthorities()
            .stream()
            .map(a -> a.getAuthority())
            .collect(Collectors.joining(","));

        String jwt = Jwts.builder()
            .claim("username", user.getUsername())
            .claim("displayName", user.displayName)
            //권한
            .claim("authorities", collect)
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