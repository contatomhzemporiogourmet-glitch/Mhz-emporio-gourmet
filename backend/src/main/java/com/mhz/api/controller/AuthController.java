package com.mhz.api.controller;

import com.mhz.api.dto.LoginRequest;
import com.mhz.api.dto.LoginResponse;
import com.mhz.api.dto.RegistroRequest;
import com.mhz.api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "https://mhzemporiogourmet.com.br"})
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody RegistroRequest request) {
        authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso");
    }
}