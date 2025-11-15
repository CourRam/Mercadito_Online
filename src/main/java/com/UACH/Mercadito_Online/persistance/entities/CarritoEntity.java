package com.UACH.Mercadito_Online.persistance.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "Carrito")
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion = new Date();

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuariosEntity usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<DetalleCarritoEntity> detalleCarrito;

    @Column(length = 30, nullable = false)
    private String estado; //  "COMPLETADA", "CANCELADA", "EN_PROCESO" (maybe creo una lista de estas opciones)

    public CarritoEntity(Long idCarrito, Date fechaCreacion, UsuariosEntity usuario, List<DetalleCarritoEntity> detalleCarrito) {
    this.idCarrito = idCarrito;
    this.fechaCreacion = fechaCreacion;
    this.usuario = usuario;
    this.detalleCarrito = detalleCarrito;
    this.estado="EN_PROCESO";
}


    
}
