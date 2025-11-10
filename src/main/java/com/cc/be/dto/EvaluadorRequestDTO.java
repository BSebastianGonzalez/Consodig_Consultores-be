package com.cc.be.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    private List<Long> lineasInvestigacionIds = new ArrayList<>();
}
