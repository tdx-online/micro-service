package com.hitwh.userservice.config.authentication;

import io.jsonwebtoken.Jwts;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Date;

public class JWTUtil {
    private static final KeyPair keyPair;

    static {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate a JWT token with username and userType
     *
     * @param username 用户名
     * @param userType 用户类型
     * @return JWT token
     */
    public static String generateToken(String username, String userType) {
        return Jwts.builder()
                .claim("usertype", userType)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(keyPair.getPrivate())
                .compact();
    }

    /**
     * Get username from token
     * @param token JWT token
     * @return username
     */
    public static String getUsernameFromToken(String token) {
        try {
            return Jwts.parser().verifyWith(keyPair.getPublic()).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get user type from token
     * @param token JWT token
     * @return user type
     */
    public static String getUserTypeFromToken(String token) {
        try {
            return Jwts.parser().verifyWith(keyPair.getPublic()).build().parseSignedClaims(token).getPayload().get("usertype", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Validate token
     * @param token JWT token
     * @return true if token is valid, false otherwise
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(keyPair.getPublic()).build().parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
