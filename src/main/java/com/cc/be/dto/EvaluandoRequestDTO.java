package com.cc.be.dto;

import lombok.Data;

@Data
public class EvaluandoRequestDTO {
    private String nombre;
    private double telefono;
    private String email;
    private String password;
    private String nivelEducativo;
}
