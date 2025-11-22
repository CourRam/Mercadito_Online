package com.UACH.Mercadito_Online.mappers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.UACH.Mercadito_Online.DTO.DetalleCarritoDTO;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.ProductosEntity;
import com.UACH.Mercadito_Online.services.DetalleCarritoService;
import com.UACH.Mercadito_Online.services.ProductosService;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class DetalleCarritoMapper {


    @Autowired
    private ProductosService productosService;

    public DetalleCarritoDTO toDTO(DetalleCarritoEntity e) {
        if (e == null) return null;
        DetalleCarritoDTO dto = new DetalleCarritoDTO();
        dto.setId(e.getIdDetalleCarrito());
        dto.setIdCarrito(e.getCarrito() != null ? e.getCarrito().getIdCarrito() : null);
        dto.setIdProducto(e.getProducto() != null ? e.getProducto().getIdProducto() : null);
        dto.setCantidad(e.getCantidad());
        dto.setSubtotal(e.getSubtotal());
        ProductosEntity producto=productosService.obtenerPorId(dto.getIdProducto());
        dto.setNombreProducto(producto.getNombre());

        return dto;
    }
}