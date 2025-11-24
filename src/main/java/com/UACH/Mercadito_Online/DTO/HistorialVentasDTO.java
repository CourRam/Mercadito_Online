package com.UACH.Mercadito_Online.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialVentasDTO {
    private Long idVenta;
    private Long idCarrito;
    private Long idUsuario;
    private LocalDate fecha;
    private BigDecimal total;

}
