package com.cc.be.dto;

import com.cc.be.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String email;
    private Rol rol;
    private String nombre;
    private String apellido;
}
