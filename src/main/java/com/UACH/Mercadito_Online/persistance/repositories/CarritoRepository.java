package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.CarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {

    List<CarritoEntity> findByUsuarioIdUsuario(Long idUsuario);

    List<CarritoEntity> findByUsuario(UsuariosEntity usuario);

    List<CarritoEntity> findByUsuarioOrderByFechaCreacionDesc(UsuariosEntity usuario);
}