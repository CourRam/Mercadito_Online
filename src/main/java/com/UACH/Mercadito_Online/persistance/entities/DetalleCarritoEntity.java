package com.UACH.Mercadito_Online.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalle_carrito")
public class DetalleCarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleCarrito;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_carrito", nullable = false)
    private CarritoEntity carrito;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductosEntity producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal = BigDecimal.ZERO;
}