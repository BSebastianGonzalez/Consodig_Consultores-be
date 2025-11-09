package com.cc.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {
    private String nombre;
    private String apellido;
    private String cedula;
    private double telefono;
    private String direccion;
    private String email;
    private String password;
}

