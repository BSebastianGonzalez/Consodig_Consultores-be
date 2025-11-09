package com.cc.be.service;

import com.cc.be.dto.EvaluadorRequestDTO;
import com.cc.be.model.Account;
import com.cc.be.model.Evaluador;
import com.cc.be.model.Rol;
import com.cc.be.repository.AccountRepository;
import com.cc.be.repository.EvaluadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluadorService {

    private final EvaluadorRepository evaluadorRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Evaluador createEvaluador(EvaluadorRequestDTO dto) {
        if (accountRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        Account account = new Account();
        account.setEmail(dto.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        account.setRol(Rol.EVALUADOR);
        account.setEnabled(true);
        Account savedAccount = accountRepository.save(account);

        Evaluador evaluador = new Evaluador();
        evaluador.setNombre(dto.getNombre());
        evaluador.setApellido(dto.getApellido());
        evaluador.setAfiliacionInstitucional(dto.getAfiliacionInstitucional());
        evaluador.setCvlac(dto.getCvlac());
        evaluador.setGoogleScholar(dto.getGoogleScholar());
        evaluador.setOrcid(dto.getOrcid());
        evaluador.setAccount(savedAccount);

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

        return evaluadorRepository.save(evaluador);
    }

    public void deactivateEvaluador(Long id) {
        Evaluador evaluador = getEvaluadorById(id);
        Account account = evaluador.getAccount();

        account.setEnabled(false);
        accountRepository.save(account);
    }
}

