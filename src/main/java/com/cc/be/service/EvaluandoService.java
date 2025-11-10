package com.cc.be.service;

import com.cc.be.dto.EvaluandoRequestDTO;
import com.cc.be.model.Account;
import com.cc.be.model.Evaluando;
import com.cc.be.model.NivelEstudios;
import com.cc.be.model.Rol;
import com.cc.be.repository.AccountRepository;
import com.cc.be.repository.EvaluandoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluandoService {

    private final AccountRepository accountRepository;
    private final EvaluandoRepository evaluandoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Evaluando createEvaluando(EvaluandoRequestDTO evaluandoRequestDTO) {
        if (accountRepository.findByEmail(evaluandoRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        Account account = new Account();
        account.setEmail(evaluandoRequestDTO.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(evaluandoRequestDTO.getPassword()));
        account.setRol(Rol.EVALUANDO);
        account.setEnabled(true);

        Account savedAccount = accountRepository.save(account);

        Evaluando evaluando = new Evaluando();
        evaluando.setNombre(evaluandoRequestDTO.getNombre());
        evaluando.setTelefono(evaluandoRequestDTO.getTelefono());
        evaluando.setAccount(savedAccount);

        // conversión segura del nivel educativo
        try {
            evaluando.setNivelEstudios(
                    NivelEstudios.valueOf(evaluandoRequestDTO.getNivelEducativo().toUpperCase())
            );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nivel educativo inválido. Valores permitidos: "
                    + Arrays.toString(NivelEstudios.values()));
        }

        return evaluandoRepository.save(evaluando);
    }

    public List<Evaluando> getAllEvaluandos() {
        return evaluandoRepository.findAll();
    }

    public Evaluando getEvaluandoById(Long id) {
        return evaluandoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluando no encontrado"));
    }

    public Evaluando updateEvaluando(Long id, EvaluandoRequestDTO evaluandoRequestDTO) {
        Evaluando evaluando = evaluandoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluando no encontrado"));

        evaluando.setNombre(evaluandoRequestDTO.getNombre());
        evaluando.setTelefono(evaluandoRequestDTO.getTelefono());

        if (evaluandoRequestDTO.getNivelEducativo() != null && !evaluandoRequestDTO.getNivelEducativo().isEmpty()) {
            try {
                evaluando.setNivelEstudios(
                        NivelEstudios.valueOf(evaluandoRequestDTO.getNivelEducativo().toUpperCase())
                );
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Nivel educativo inválido. Valores permitidos: "
                        + Arrays.toString(NivelEstudios.values()));
            }
        }

        return evaluandoRepository.save(evaluando);
    }

    public void deactivateEvaluando(Long id) {
        Evaluando evaluando = evaluandoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluando no encontrado"));

        Account account = evaluando.getAccount();
        account.setEnabled(false);
        accountRepository.save(account);
    }
}
