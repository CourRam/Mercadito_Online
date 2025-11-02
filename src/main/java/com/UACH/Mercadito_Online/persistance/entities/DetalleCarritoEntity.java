package com.UACH.Mercadito_Online.persistance.entities;


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
    private DetalleCarritoId id;

    private Integer cantidad;

    @ManyToOne
    @MapsId("idCarrito")
    @JoinColumn(name = "id_carrito")
    private CarritoEntity carrito;

    @ManyToOne
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private ProductosEntity producto;

    
}
