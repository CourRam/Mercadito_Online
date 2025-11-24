package com.UACH.Mercadito_Online.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//DTO es "data transfer object"
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosDTO {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String imagenUrl;
    private Long categoriaId;
    private Long vendedorId;
    private Integer stock;
    private Integer stockInicial;
    private Integer stockVendido;
    private BigDecimal gananciaTotal;
}
