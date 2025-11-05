package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {

    Optional<UsuariosEntity> findByCorreo(String correo);
}