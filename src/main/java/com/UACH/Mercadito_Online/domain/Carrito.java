package com.UACH.Mercadito_Online.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
    private Long id_carrito;
    private Usuario usuario;
    private String estatus;
    private List<DetalleCarrito> items = new ArrayList<>();
}
