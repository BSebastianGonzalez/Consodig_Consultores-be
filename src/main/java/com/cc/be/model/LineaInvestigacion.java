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
@AllArgsConstructor
@NoArgsConstructor
public class LineaInvestigacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    @ManyToMany(mappedBy = "lineasInvestigacion")
    @JsonBackReference
    private List<Evaluador> evaluadores = new ArrayList<>();

    @ManyToMany(mappedBy = "lineasInvestigacion")
    @JsonBackReference
    private List<Evaluando> evaluandos = new ArrayList<>();
}
