package com.UACH.Mercadito_Online.mappers;



import org.springframework.stereotype.Component;

import com.UACH.Mercadito_Online.DTO.HistorialVentasDTO;
import com.UACH.Mercadito_Online.persistance.entities.HistorialVentasEntity;

@Component
public class HistorialVentasMapper {

    public HistorialVentasDTO toDTO(HistorialVentasEntity entity) {
        if (entity == null) return null;

        HistorialVentasDTO dto = new HistorialVentasDTO();
        dto.setIdVenta(entity.getIdVenta());
        dto.setIdCarrito(entity.getCarrito() != null ? entity.getCarrito().getIdCarrito() : null);
        dto.setIdUsuario(entity.getUsuario() != null ? entity.getUsuario().getIdUsuario() : null);
        dto.setFecha(entity.getFecha());
        dto.setTotal(entity.getTotal());

        return dto;
    }

    
}
