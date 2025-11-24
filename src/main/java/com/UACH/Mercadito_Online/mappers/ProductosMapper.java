package com.UACH.Mercadito_Online.mappers;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.UACH.Mercadito_Online.DTO.ProductosDTO;
import com.UACH.Mercadito_Online.persistance.entities.ProductosEntity;
import com.UACH.Mercadito_Online.persistance.entities.CategoriasEntity;
import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;

@Component
public class ProductosMapper {

    // Entity → DTO
    public ProductosDTO toDTO(ProductosEntity entity) {
        if (entity == null) return null;

        ProductosDTO dto = new ProductosDTO();
        dto.setIdProducto(entity.getIdProducto());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setImagenUrl(entity.getImagenUrl());
        dto.setStock(entity.getStock());
        dto.setStockInicial(entity.getStockInicial());
        dto.setCategoriaId(entity.getCategoria() != null ? entity.getCategoria().getIdCategoria() : null);
        dto.setVendedorId(entity.getUsuario() != null ? entity.getUsuario().getIdUsuario() : null);
        dto.setStockInicial(entity.getStockInicial());
        dto.setStockVendido(entity.getStockInicial()-entity.getStock());
        dto.setGananciaTotal(BigDecimal.valueOf(entity.getStockInicial()-entity.getStock()).multiply(entity.getPrecio()));

        return dto;
    }

    // DTO → Entity
    public ProductosEntity toEntity(ProductosDTO dto) {
        if (dto == null) return null;

        ProductosEntity entity = new ProductosEntity();
        entity.setIdProducto(dto.getIdProducto());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setImagenUrl(dto.getImagenUrl());
        entity.setStock(dto.getStock());

        // Relaciones: aquí solo creamos entidades con el ID,
        // el service debe cargar las entidades completas desde el repositorio
        if (dto.getCategoriaId() != null) {
            CategoriasEntity categoria = new CategoriasEntity();
            categoria.setIdCategoria(dto.getCategoriaId());
            entity.setCategoria(categoria);
        }

        if (dto.getVendedorId() != null) {
            UsuariosEntity usuario = new UsuariosEntity();
            usuario.setIdUsuario(dto.getVendedorId());
            entity.setUsuario(usuario);
        }

        return entity;
    }
}