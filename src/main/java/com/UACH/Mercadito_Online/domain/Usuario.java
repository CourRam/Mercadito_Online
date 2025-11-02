package com.UACH.Mercadito_Online.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    
    private Long id_usuario;

    private String nombre;

    
    private String email;

    
    private String contrasena; // hash

    private String telefono;
    private String activo;

    
    private Role role;

    
    private Sucursal sucursal;

}
