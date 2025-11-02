package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {

    UsuariosEntity findByCorreo(String correo);
}