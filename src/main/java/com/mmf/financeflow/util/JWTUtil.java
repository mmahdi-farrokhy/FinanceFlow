package com.mmf.financeflow.util;

import com.mmf.financeflow.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JWTUtil {
    private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24;
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private JWTUtil() {
    }

    public static String generateToken(String username, Set<UserRole> roles) {
        Set<String> stringRoles = roles.stream().map(UserRole::name).collect(Collectors.toSet());

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", stringRoles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(KEY)
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public static Set<String> getRolesFromToken(String token) {
        Object roles = getClaims(token).get("roles");
        if (roles instanceof Set<?>) {
            return ((Set<?>) roles).stream().map(Object::toString).collect(Collectors.toSet());
        }
        if (roles instanceof java.util.List<?>) {
            return ((java.util.List<?>) roles).stream().map(Object::toString).collect(Collectors.toSet());
        }
        return Set.of();
    }

    public static boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private static Claims getClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
