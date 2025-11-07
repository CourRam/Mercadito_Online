package com.UACH.Mercadito_Online.persistance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@Table(name = "HistorialVentas")
public class HistorialVentasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito", nullable = false)
    private CarritoEntity carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuariosEntity usuario;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;


    @Column(length = 30, nullable = false)
    private String estado; //  "COMPLETADA", "CANCELADA", "EN_PROCESO" (maybe creo una lista de estas opciones)



    public HistorialVentasEntity(CarritoEntity carrito, UsuariosEntity usuario, BigDecimal total, String estado) {
        this.carrito = carrito;
        this.usuario = usuario;
        this.fecha = LocalDateTime.now();
        this.total = total;
        this.estado = estado;
    }
    
}