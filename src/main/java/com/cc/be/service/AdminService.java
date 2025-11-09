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

import java.util.List;

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

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
    }

    public Admin updateAdmin(Long id, AdminRequestDTO dto) {
        Admin admin = getAdminById(id);

        admin.setNombre(dto.getNombre());
        admin.setApellido(dto.getApellido());
        admin.setCedula(dto.getCedula());
        admin.setTelefono(dto.getTelefono());
        admin.setDireccion(dto.getDireccion());

        return adminRepository.save(admin);
    }

    public void deactivateAdmin(Long id) {
        Admin admin = getAdminById(id);
        Account account = admin.getAccount();

        account.setEnabled(false);
        accountRepository.save(account);
    }
}
