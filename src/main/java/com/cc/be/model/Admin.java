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
public class Admin {
    @Id
    private Long id;

    private String nombre;
    private String apellido;
    private String cedula;
    private double telefono;
    private String direccion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @JsonBackReference
    private Account account;
}
