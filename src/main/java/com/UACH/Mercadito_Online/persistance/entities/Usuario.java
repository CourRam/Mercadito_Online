package com.UACH.Mercadito_Online.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String contrasena; // hash

    private String telefono;
    private String activo;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

}
