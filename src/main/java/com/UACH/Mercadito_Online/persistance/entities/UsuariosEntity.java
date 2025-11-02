package com.UACH.Mercadito_Online.persistance.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuarios")
public class UsuariosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombre;
    @Column(unique = true, nullable = false)
    private String correo;
    @Column(nullable = false)
    private String contraseña;
    private String telefono;
    private String direccion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<ProductosEntity> productos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<CarritoEntity> carritos;

}
