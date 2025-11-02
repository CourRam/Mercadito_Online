package com.UACH.Mercadito_Online.persistance.repositories;

import com.UACH.Mercadito_Online.persistance.entities.CategoriasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoriasRepository extends JpaRepository<CategoriasEntity, Long> {
    CategoriasEntity findByNombre(String nombre);
}