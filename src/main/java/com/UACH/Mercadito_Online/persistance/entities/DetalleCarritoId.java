package com.UACH.Mercadito_Online.persistance.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DetalleCarritoId implements Serializable {

    private Long idCarrito;
    private Long idProducto;

    // Getters, setters, equals, hashCode
    public Long getIdCarrito() { return idCarrito; }
    public void setIdCarrito(Long idCarrito) { this.idCarrito = idCarrito; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleCarritoId)) return false;
        DetalleCarritoId that = (DetalleCarritoId) o;
        return Objects.equals(idCarrito, that.idCarrito) && Objects.equals(idProducto, that.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarrito, idProducto);
    }
}
