package com.UACH.Mercadito_Online.persistance.entities;


import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DetalleCarrito")
public class DetalleCarritoEntity {

    @EmbeddedId
    private DetalleCarritoID id;

    private Integer cantidad;

    @ManyToOne
    @MapsId("idCarrito")
    @JoinColumn(name = "id_carrito")
    private CarritoEntity carrito;

    @ManyToOne
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private ProductosEntity producto;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;
}
