package com.cc.be.service;

import com.cc.be.dto.LoginRequestDTO;
import com.cc.be.dto.LoginResponseDTO;
import com.cc.be.model.*;
import com.cc.be.repository.AccountRepository;
import com.cc.be.repository.AdminRepository;
import com.cc.be.repository.EvaluadorRepository;
import com.cc.be.repository.EvaluandoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final AdminRepository adminRepository;
    private final EvaluadorRepository evaluadorRepository;
    private final EvaluandoRepository evaluandoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        Account account = accountRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        if (!account.isEnabled()) {
            throw new RuntimeException("Cuenta deshabilitada");
        }

        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        switch (account.getRol()) {
            case ADMIN -> {
                Admin admin = adminRepository.findById(account.getId())
                        .orElseThrow(() -> new RuntimeException("No se encontró el admin"));
                return new LoginResponseDTO(admin.getId(), account.getEmail(), Rol.ADMIN,
                        admin.getNombre(), admin.getApellido());
            }
            case EVALUADOR -> {
                Evaluador eval = evaluadorRepository.findById(account.getId())
                        .orElseThrow(() -> new RuntimeException("No se encontró el evaluador"));
                return new LoginResponseDTO(eval.getId(), account.getEmail(), Rol.EVALUADOR,
                        eval.getNombre(), eval.getApellido());
            }
            case EVALUANDO -> {
                Evaluando evald = evaluandoRepository.findById(account.getId())
                        .orElseThrow(() -> new RuntimeException("No se encontró el evaluando"));
                return new LoginResponseDTO(evald.getId(), account.getEmail(), Rol.EVALUANDO,
                        evald.getNombre(), null);
            }
            default -> throw new RuntimeException("Rol desconocido");
        }
    }
    }
