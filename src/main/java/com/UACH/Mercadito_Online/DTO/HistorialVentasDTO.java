package com.UACH.Mercadito_Online.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime fecha;
    private BigDecimal total;

}
