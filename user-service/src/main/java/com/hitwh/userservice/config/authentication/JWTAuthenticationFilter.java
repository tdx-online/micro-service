package com.hitwh.userservice.config.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.concurrent.TimeUnit;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final RedisTemplate<String, Object> redisTemplate;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String token = JWTUtil.generateToken(authResult.getName(), "");
        redisTemplate.opsForValue().set(authResult.getName(), token, 1, TimeUnit.MINUTES);
        response.addHeader("Authorization", "Bearer " + token);
    }
}

