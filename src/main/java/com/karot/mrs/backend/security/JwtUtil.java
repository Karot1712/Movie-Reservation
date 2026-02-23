package com.karot.mrs.backend.security;


import com.karot.mrs.backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtil {
    @Value("${secretJwtString}")
    private String secretJwtString;

    @Value("${expirationDate}")
    private Long expirationDate; //24 hours

    private SecretKey secretKey;

    @PostConstruct
    private void init(){
        byte[] keyBytes =secretJwtString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(keyBytes,"HmacSHA256");
    }

    public String generateToken(UserDetails userDetails){
        AuthUser user  =(AuthUser) userDetails;
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getId());
        claims.put("role",user.getRole());
        return createToken(user.getUsername(),claims);
    }

    public String createToken(String userName, Map<String,Object> claims){
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationDate))
                .signWith(secretKey)
                .compact();
    }

    private <T>T extractClaims (String token, Function<Claims,T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }

    public String getEmailFromToken(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Long getUserIdFromToken(String token){
        return extractClaims(token,claims -> claims.get("userId",Long.class));
    }

    public Date getTokenExpirationDate(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        try{
            final String userName = getEmailFromToken(token);
            return(userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }catch (Exception e){
            log.error("Toke validation failed : {}",e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }
}
