package com.UACH.Mercadito_Online.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HistorialVentas")
public class HistorialVentasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @ManyToOne
    @JoinColumn(name = "id_comprador")
    private UsuariosEntity comprador;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private ProductosEntity producto;

    private Integer cantidad;
    private Double total;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenta = new Date();

    
}