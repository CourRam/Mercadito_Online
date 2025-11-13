package com.UACH.Mercadito_Online.persistance.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuarios")
public class UsuariosEntity {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 20)
    private String telefono;

    @Column(length = 255)
    private String direccion;

    // Relación con productos (un usuario puede publicar varios productos)
    //@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<ProductosEntity> productos;

}
