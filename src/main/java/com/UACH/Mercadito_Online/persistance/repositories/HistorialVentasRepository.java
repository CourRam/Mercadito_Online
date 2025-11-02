package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.HistorialVentasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialVentasRepository extends JpaRepository<HistorialVentasEntity, Long> {
    List<HistorialVentasEntity> findByCompradorIdUsuario(Long idUsuario);
    List<HistorialVentasEntity> findByProductoIdProducto(Long idProducto);
}