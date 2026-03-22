package com.grupo04.Biblioteca.Service;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String gerarToken(String usuario) {

        return Jwts.builder()
                .setSubject(usuario) // usuário dono do token
                .setIssuedAt(new Date()) // data de criação
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // expira em 24h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
