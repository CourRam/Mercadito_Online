package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.HistorialVentasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HistorialVentasRepository extends JpaRepository<HistorialVentasEntity, Long> {
    List<HistorialVentasEntity> findByUsuario_IdUsuario(Long idUsuario);

    /* No jala mi chingadera
    @Query("""
        SELECT COALESCE(SUM(d.subtotal), 0)
        FROM HistorialVentasEntity h
        JOIN h.carrito c
        JOIN DetalleCarritoEntity d ON d.carrito = c
        JOIN ProductosEntity p ON d.producto = p
        WHERE p.vendedor.idUsuario = :idUsuario
          AND h.estado = 'COMPLETADA'
    """)
    BigDecimal calcularGananciasPorVendedor(@Param("idUsuario") Long idUsuario);
     aver si asi si quiere eesta madre*/
    @Query("""
    SELECT COALESCE(SUM(d.subtotal), 0)
    FROM HistorialVentasEntity h
    JOIN h.carrito c
    JOIN c.detalleCarrito d
    JOIN d.producto p
    WHERE p.usuario.idUsuario = :idUsuario
    AND h.estado = 'COMPLETADA'
    """)
    BigDecimal calcularGananciasPorVendedor(@Param("idUsuario") Long idUsuario);

    //List<HistorialVentasEntity> findByProductoIdProducto(Long idProducto); Arreglar si se llega a usar
    /* Posible solucion no probada
    // Buscar todas las ventas donde el carrito contenga el producto indicado
    @Query("""
        SELECT hv
        FROM HistorialVentasEntity hv
        JOIN hv.carrito c
        JOIN c.detalleCarrito d
        WHERE d.producto.idProducto = :idProducto
    """)
    List<HistorialVentasEntity> findByProductoId(@Param("idProducto") Long idProducto);
     */
}