package com.cc.be.controller;

import com.cc.be.dto.AdminRequestDTO;
import com.cc.be.model.Admin;
import com.cc.be.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminRequestDTO dto) {
        Admin created = adminService.createAdmin(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody AdminRequestDTO dto) {
        return ResponseEntity.ok(adminService.updateAdmin(id, dto));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateAdmin(@PathVariable Long id) {
        adminService.deactivateAdmin(id);
        return ResponseEntity.ok("Administrador desactivado correctamente");
    }
}
