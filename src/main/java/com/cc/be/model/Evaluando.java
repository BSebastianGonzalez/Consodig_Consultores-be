package com.cc.be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluando {
    @Id
    private Long id;
    private String nombre;
    private double telefono;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @JsonBackReference
    @MapsId
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "evaluador_linea_investigacion",
            joinColumns = @JoinColumn(name = "evaluador_id"),
            inverseJoinColumns = @JoinColumn(name = "linea_investigacion_id")
    )
    @JsonManagedReference
    private List<LineaInvestigacion> lineasInvestigacion = new ArrayList<>();
    private NivelEstudios nivelEstudios;


}
