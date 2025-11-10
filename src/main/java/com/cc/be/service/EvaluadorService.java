package com.cc.be.service;

import com.cc.be.dto.EvaluadorRequestDTO;
import com.cc.be.model.Account;
import com.cc.be.model.Evaluador;
import com.cc.be.model.NivelEstudios;
import com.cc.be.model.Rol;
import com.cc.be.repository.AccountRepository;
import com.cc.be.repository.EvaluadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluadorService {

    private final EvaluadorRepository evaluadorRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Evaluador createEvaluador(EvaluadorRequestDTO evaluadorRequestDTO) {
        if (accountRepository.findByEmail(evaluadorRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        Account account = new Account();
        account.setEmail(evaluadorRequestDTO.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(evaluadorRequestDTO.getPassword()));
        account.setRol(Rol.EVALUADOR);
        account.setEnabled(true);

        Account savedAccount = accountRepository.save(account);

        Evaluador evaluador = new Evaluador();
        evaluador.setNombre(evaluadorRequestDTO.getNombre());
        evaluador.setApellido(evaluadorRequestDTO.getApellido());
        evaluador.setAfiliacionInstitucional(evaluadorRequestDTO.getAfiliacionInstitucional());
        evaluador.setCvlac(evaluadorRequestDTO.getCvlac());
        evaluador.setGoogleScholar(evaluadorRequestDTO.getGoogleScholar());
        evaluador.setOrcid(evaluadorRequestDTO.getOrcid());
        evaluador.setAccount(savedAccount);

        try {
            evaluador.setNivelEducativo(
                    NivelEstudios.valueOf(evaluadorRequestDTO.getNivelEducativo().toUpperCase())
            );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nivel educativo inválido. Valores permitidos: " + Arrays.toString(NivelEstudios.values()));
        }

        return evaluadorRepository.save(evaluador);
    }

    public List<Evaluador> getAllEvaluadores() {
        return evaluadorRepository.findAll();
    }

    public Evaluador getEvaluadorById(Long id) {
        return evaluadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluador no encontrado"));
    }

    public Evaluador updateEvaluador(Long id, EvaluadorRequestDTO dto) {
        Evaluador evaluador = getEvaluadorById(id);

        evaluador.setNombre(dto.getNombre());
        evaluador.setApellido(dto.getApellido());
        evaluador.setAfiliacionInstitucional(dto.getAfiliacionInstitucional());
        evaluador.setCvlac(dto.getCvlac());
        evaluador.setGoogleScholar(dto.getGoogleScholar());
        evaluador.setOrcid(dto.getOrcid());

        if (dto.getNivelEducativo() != null && !dto.getNivelEducativo().isEmpty()) {
            try {
                evaluador.setNivelEducativo(
                        NivelEstudios.valueOf(dto.getNivelEducativo().toUpperCase())
                );
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Nivel educativo inválido. Valores permitidos: "
                        + Arrays.toString(NivelEstudios.values()));
            }
        }

        return evaluadorRepository.save(evaluador);
    }

    public void deactivateEvaluador(Long id) {
        Evaluador evaluador = getEvaluadorById(id);
        Account account = evaluador.getAccount();

        account.setEnabled(false);
        accountRepository.save(account);
    }
}

