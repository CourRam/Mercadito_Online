package com.UACH.Mercadito_Online.mappers;

import com.UACH.Mercadito_Online.DTO.CarritoDTO;
import com.UACH.Mercadito_Online.DTO.DetalleCarritoDTO;
import com.UACH.Mercadito_Online.persistance.entities.CarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoMapper {

    private CarritoMapper() { /* util */ }

    public static CarritoDTO toDTO(CarritoEntity carrito) {
        if (carrito == null) return null;

        CarritoDTO dto = new CarritoDTO();
        dto.setIdCarrito(carrito.getIdCarrito());
        dto.setEstado(carrito.getEstado() == null ? "EN_PROCESO" : carrito.getEstado());

        List<DetalleCarritoEntity> detallesEntity = carrito.getDetalleCarrito();
        List<DetalleCarritoDTO> detallesDto;
        if (detallesEntity == null) {
            detallesDto = Collections.emptyList();
        } else {
            detallesDto = detallesEntity.stream()
                    .map(DetalleCarritoMapper::toDTO)
                    .collect(Collectors.toList());
        }

        dto.setDetalles(detallesDto);

        BigDecimal total = detallesDto.stream()
                .map(d -> d.getSubtotal() == null ? BigDecimal.ZERO : d.getSubtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotal(total);

        return dto;
    }
}
