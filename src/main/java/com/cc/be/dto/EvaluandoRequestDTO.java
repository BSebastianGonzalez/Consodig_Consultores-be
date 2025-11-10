package com.cc.be.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EvaluandoRequestDTO {
    private String nombre;
    private double telefono;
    private String email;
    private String password;
    private String nivelEducativo;
    private List<Long> lineasInvestigacionIds = new ArrayList<>();
}
