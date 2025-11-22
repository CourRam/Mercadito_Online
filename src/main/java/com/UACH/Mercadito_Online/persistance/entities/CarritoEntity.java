package com.UACH.Mercadito_Online.persistance.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Carrito")
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion = new Date();

    // 🔥 Evita ciclo: Usuario → Carritos → Usuario → ...
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference(value = "usuario-carritos")
    private UsuariosEntity usuario;

    // 🔥 Evita ciclo: Carrito → Detalles → Carrito → ...
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "carrito-detalles")
    private List<DetalleCarritoEntity> detalleCarrito;

    @Column(length = 30, nullable = false)
    private String estado = "EN_PROCESO";

    public CarritoEntity(Long idCarrito, Date fechaCreacion, UsuariosEntity usuario,
                         List<DetalleCarritoEntity> detalleCarrito) {
        this.idCarrito = idCarrito;
        this.usuario = usuario;
        this.detalleCarrito = detalleCarrito;
        this.estado = "EN_PROCESO";
    }

    public CarritoEntity(UsuariosEntity usuario, Date fechaCreacion) {
        this.usuario = usuario;
        this.estado = "EN_PROCESO";
    }
}
