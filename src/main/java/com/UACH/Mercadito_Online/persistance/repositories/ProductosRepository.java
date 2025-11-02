package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.ProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosEntity, Long> {
    List<ProductosEntity> findByUsuarioIdUsuario(Long idUsuario);
    List<ProductosEntity> findByCategoriaIdCategoria(Long idCategoria);
    List<ProductosEntity> findByNombreContainingIgnoreCase(String nombre);
}