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
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;

    private String nombre;
    @Column(length = 1000)
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String activo;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Usuario vendedor;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;
}
