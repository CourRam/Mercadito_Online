package com.UACH.Mercadito_Online.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetalleCarritoDTO {
    private Long id;
    private Long idCarrito;
    private Long idProducto;
    private Integer cantidad;
    private BigDecimal subtotal;
    private String nombreProducto;
}
