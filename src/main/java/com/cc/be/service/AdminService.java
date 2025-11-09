package com.cc.be.service;

import com.cc.be.dto.AdminRequestDTO;
import com.cc.be.model.Account;
import com.cc.be.model.Admin;
import com.cc.be.model.Rol;
import com.cc.be.repository.AccountRepository;
import com.cc.be.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AccountRepository accountRepository;
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Admin createAdmin(AdminRequestDTO adminRequestDTO) {
        if (accountRepository.findByEmail(adminRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        Account account = new Account();
        account.setEmail(adminRequestDTO.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(adminRequestDTO.getPassword()));
        account.setRol(Rol.ADMIN);
        account.setEnabled(true);

        Account savedAccount = accountRepository.save(account);

        Admin admin = new Admin();
        admin.setNombre(adminRequestDTO.getNombre());
        admin.setApellido(adminRequestDTO.getApellido());
        admin.setCedula(adminRequestDTO.getCedula());
        admin.setTelefono(adminRequestDTO.getTelefono());
        admin.setDireccion(adminRequestDTO.getDireccion());
        admin.setAccount(savedAccount);

        return adminRepository.save(admin);
    }

}
