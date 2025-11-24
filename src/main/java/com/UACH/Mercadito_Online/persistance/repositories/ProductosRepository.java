package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.ProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosEntity, Long> {
    List<ProductosEntity> findByUsuarioIdUsuario(Long idUsuario);

    List<ProductosEntity> findByCategoriaIdCategoria(Long idCategoria);

    List<ProductosEntity> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT p FROM ProductosEntity p WHERE p.usuario.idUsuario = :idUsuario AND p.stock > 0")
    List<ProductosEntity> obtenerActivosPorUsuario(@Param("idUsuario") Long idUsuario);

    @Query("SELECT p FROM ProductosEntity p WHERE p.usuario.idUsuario = :idUsuario AND p.stock <>p.stockInicial")
    List<ProductosEntity> obtenerVendidosPorUsuario(@Param("idUsuario") Long idUsuario);
}