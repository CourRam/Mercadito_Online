package com.UACH.Mercadito_Online.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarritoDTO {

    private Long idCarrito;
    private List<DetalleCarritoDTO> detalles;
    private BigDecimal total;
    private String estado;
}