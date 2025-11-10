package com.cc.be.dto;

import lombok.Data;

@Data
public class EvaluadorRequestDTO {
    private String nombre;
    private String apellido;
    private String afiliacionInstitucional;
    private String cvlac;
    private String googleScholar;
    private String orcid;
    private String email;
    private String password;
    private String nivelEducativo;
}
