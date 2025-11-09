package com.cc.be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluador {
    @Id
    private Long id;
    private String nombre;
    private String apellido;
    private String afiliacionInstitucional;
    private String cvlac;
    private String googleScholar;
    private String orcid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @JsonBackReference
    @MapsId
    private Account account;
}
