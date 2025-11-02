package com.UACH.Mercadito_Online.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCarrito {
   
    private Long id_detalle;

    
    private Carrito carrito;

    
    private Producto producto;

    private Integer cantidad;
    private BigDecimal precio_unitario;
}
