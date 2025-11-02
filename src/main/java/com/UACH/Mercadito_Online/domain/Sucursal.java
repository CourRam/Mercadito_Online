package com.UACH.Mercadito_Online.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class Sucursal {
    
    private Long id_sucursal;

    private String nombre;
    private String direccion;

   
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
