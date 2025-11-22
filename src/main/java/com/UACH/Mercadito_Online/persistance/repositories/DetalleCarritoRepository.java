package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoID;
import com.UACH.Mercadito_Online.persistance.entities.CarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.ProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleCarritoRepository extends JpaRepository<DetalleCarritoEntity, DetalleCarritoID> {

    // Obtener todos los detalles del carrito
    List<DetalleCarritoEntity> findByCarrito_IdCarrito(Long idCarrito);

    // Buscar detalle específico (carrito + producto)
    Optional<DetalleCarritoEntity> findByCarritoAndProducto(
            CarritoEntity carrito,
            ProductosEntity producto
    );

    List<DetalleCarritoEntity> findByCarrito(CarritoEntity carrito);
}