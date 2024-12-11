package com.hw.shopping.controller;

import com.hw.shopping.security.JwtUtil;
import com.hw.shopping.service.CustomUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtCookies = "";
        for (int i = 0; i < cookies.length; i++) {
            System.out.println("####### cookie 이름 꺼내기 ########");
            System.out.println("cookies = " + cookies[i].getName());
            System.out.println("####### cookie 이름 꺼내기 ########");

            if (cookies[i].getName().equals("jwt")) {

                jwtCookies = cookies[i].getValue();

                System.out.println("######JwtFiter#######");
                System.out.println("jwtCookies = " + jwtCookies);
                System.out.println("######JwtFiter#######");
            }

        }


        Claims claim;
        try {
             claim = JwtUtil.extractToken(jwtCookies);
            System.out.println("###########Claims###########");
            System.out.println("claim = " + claim);
            System.out.println("############Claims###########");
        } catch (Exception e) {
            System.out.println("유효기간 만료");
            filterChain.doFilter(request, response);
            return ;
        }

        String[] arr = claim.get("authorities").toString().split(",");

        System.out.println("###########arr1111###########");
        System.out.println("arr = " + arr);
        System.out.println("############arr1111###########");

        List<SimpleGrantedAuthority> authorities = Arrays.stream(arr)
            .map(a -> new SimpleGrantedAuthority(a)).toList();

        System.out.println("###########authorities2222###########");
        System.out.println("authorities = " + authorities);
        System.out.println("############authorities2222###########");

        CustomUser customUser = new CustomUser(claim.get("username").toString(), "none",
                                               authorities);

        System.out.println("###########customUser3333###########");
        System.out.println("customUser = " + customUser);
        System.out.println("authorities = " + authorities);
        System.out.println("############customUser333###########");

        customUser.displayName = claim.get("displayName").toString();

        System.out.println("########### customUser.displayName444444###########");
        System.out.println(" customUser.displayName = " +  customUser.displayName);
        System.out.println("############ customUser.displayName44444###########");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            customUser,
                null, authorities
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);


        filterChain.doFilter(request, response);
    }
    }