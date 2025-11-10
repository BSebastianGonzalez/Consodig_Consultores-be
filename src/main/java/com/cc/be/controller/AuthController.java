package com.cc.be.controller;

import com.cc.be.dto.LoginRequestDTO;
import com.cc.be.dto.LoginResponseDTO;
import com.cc.be.dto.ResetPasswordRequest;
import com.cc.be.repository.AccountRepository;
import com.cc.be.service.AuthService;
import com.cc.be.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;
    private final AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        passwordResetService.sendResetLink(correo);
        return ResponseEntity.ok("Se ha enviado un enlace de recuperación al correo.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("La contraseña ha sido cambiada correctamente.");
    }

    @GetMapping("/email/{id}")
    public ResponseEntity<String> getEmailByAccountId(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(account -> ResponseEntity.ok(account.getEmail()))
                .orElse(ResponseEntity.notFound().build());
    }
}
