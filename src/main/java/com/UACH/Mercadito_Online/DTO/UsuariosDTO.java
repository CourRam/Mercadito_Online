package com.UACH.Mercadito_Online.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosDTO {
    private String nombre;
    private String correo;
    private String password;
    private String telefono;
    private String direccion;
}