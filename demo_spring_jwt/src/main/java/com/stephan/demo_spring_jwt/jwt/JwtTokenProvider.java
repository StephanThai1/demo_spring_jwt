package com.stephan.demo_spring_jwt.jwt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "stephanA";

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long JWT_EXPIRATION = 604800000L;
    private Gson gson = new Gson();

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        String payload = userDetails.getUsername();
        Map<String,Object> test= new HashMap<>();
        test.put("test","The king");
        return Jwts.builder()
                .setSubject(payload)
                .setClaims(test)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String decodeToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
//        JsonObject object = JsonParser.parseString(claims.getSubject()).getAsJsonObject();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
