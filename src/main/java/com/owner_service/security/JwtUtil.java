package com.owner_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public UUID extractUserId(String token) {

        String userId = extractAllClaims(token)
                .get("userId", String.class);

        return UUID.fromString(userId);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * Extracts all claims (payload) from the JWT token
     * after verifying its signature.
     *
     * JWT Format:
     * header.payload.signature
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                // Step 1:
                // verifyWith(key) -> Ye wahi secret key use karta hai
                // jo application.properties (app.jwt.secret) me hai.
                // Ye key security-service wali key ke exactly same honi chahiye.
                .verifyWith(key)

                // Step 2:
                // Parser build karta hai jo JWT ko verify aur parse karega.
                .build()

                // Step 3:
                // Token ka signature dobara isi secret key se verify hota hai.
                //
                // Agar signature match ho gaya matlab:
                // Token security-service ne hi generate kiya tha.
                // Token ke payload (claims) me kisi ne koi changes nahi kiye.
                //
                // Agar:
                // Secret key galat hui
                // Ya token ke andar data edit kiya gaya
                //
                // To yahi line exception throw karegi aur request reject ho jayegi.
                .parseSignedClaims(token)

                // Step 4:
                // Signature successfully verify hone ke baad
                // payload (claims) return karta hai.
                //
                // Claims ke andar hume milta hai:
                // - subject (email)
                // - id
                // - username
                // - role
                .getPayload();
    }
}

