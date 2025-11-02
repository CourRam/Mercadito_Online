package com.UACH.Mercadito_Online.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    private Long id_producto;

    private String nombre;
    
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String activo;

   
    private Usuario vendedor;

   
    private Sucursal sucursal;
}
