package com.cc.be.controller;

import com.cc.be.dto.AdminRequestDTO;
import com.cc.be.model.Admin;
import com.cc.be.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
