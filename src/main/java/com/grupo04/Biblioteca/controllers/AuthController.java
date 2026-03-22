package com.grupo04.Biblioteca.controllers;

import com.grupo04.Biblioteca.Service.TokenService;
import com.grupo04.Biblioteca.dto.LoginRequest;
import com.grupo04.Biblioteca.models.BibliotecarioModel;
import com.grupo04.Biblioteca.repository.BibliotecarioRepository;
import com.grupo04.Biblioteca.security.SenhaCriptografada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        String hash = SenhaCriptografada.hash(request.getSenha());

        BibliotecarioModel bibliotecario = bibliotecarioRepository.findByDsEmail(request.getEmail());

        if (!bibliotecario.getCdSenha().equals(hash)) {
            return ResponseEntity
                    .status(401)
                    .body("Senha inválida");
        }
        var token = tokenService.gerarToken(bibliotecario.getDsEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }
}