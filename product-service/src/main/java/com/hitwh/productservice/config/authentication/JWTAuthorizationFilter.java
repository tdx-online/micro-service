package com.hitwh.productservice.config.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final RedisTemplate<String, Object> redisTemplate;

    public JWTAuthorizationFilter(AuthenticationManager authManager, RedisTemplate<String, Object> redisTemplate) {
        super(authManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        String username = JWTUtil.getUsernameFromToken(token);

        String requestURI = request.getRequestURI();
        System.out.println(requestURI);

        if (username == null) {
            if (requestURI.contains("user/register") ||
                    requestURI.contains("user/login") ||
                    requestURI.contains("product/details/{id}") ||
                    requestURI.contains("product/list") ||
                    requestURI.contains("category/homePageCategory") ||
                    requestURI.contains("category/listAllCategories") ||
                    requestURI.contains("category/searchCategoryProperty/{cid}")) {
                chain.doFilter(request, response);
                return;
            }
//            System.out.println(requestURI);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (JWTUtil.validateToken(token)) {
            try {
                if (redisTemplate.opsForValue().get(token) != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                return;
//                e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
    }
}
