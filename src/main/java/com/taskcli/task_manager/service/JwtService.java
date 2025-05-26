package com.taskcli.task_manager.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
//    Secret key for signing
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

//    Token expiration: 24 hours
    private final long EXPIRATION_TIME = 1000*60*60*24;

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
}
