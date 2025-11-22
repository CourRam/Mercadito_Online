package com.UACH.Mercadito_Online.mappers;

import com.UACH.Mercadito_Online.DTO.DetalleCarritoDTO;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoEntity;

public class DetalleCarritoMapper {

    private DetalleCarritoMapper() { /* util */ }

    public static DetalleCarritoDTO toDTO(DetalleCarritoEntity e) {
        if (e == null) return null;
        DetalleCarritoDTO dto = new DetalleCarritoDTO();
        dto.setId(e.getIdDetalleCarrito());
        dto.setIdCarrito(e.getCarrito() != null ? e.getCarrito().getIdCarrito() : null);
        dto.setIdProducto(e.getProducto() != null ? e.getProducto().getIdProducto() : null);
        dto.setCantidad(e.getCantidad());
        dto.setSubtotal(e.getSubtotal());
        return dto;
    }
}