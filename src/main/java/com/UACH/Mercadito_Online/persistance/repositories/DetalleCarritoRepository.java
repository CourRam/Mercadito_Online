package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.CarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCarritoRepository extends JpaRepository<DetalleCarritoEntity, DetalleCarritoID> {
    List<DetalleCarritoEntity> findByCarritoIdCarrito(Long idCarrito);
    List<DetalleCarritoEntity> findByCarrito(CarritoEntity carrito);

    List<DetalleCarritoEntity> findByCarrito_IdCarrito(Long idCarrito);

}