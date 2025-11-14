package com.UACH.Mercadito_Online.services;

import com.UACH.Mercadito_Online.DTO.CategoriasDTO;
import com.UACH.Mercadito_Online.DTO.ProductosDTO;
import com.UACH.Mercadito_Online.persistance.entities.CategoriasEntity;
import com.UACH.Mercadito_Online.persistance.repositories.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriasService {

    @Autowired
    private CategoriasRepository categoriasRepository;

    // Crear nueva categoría
    public CategoriasEntity crearCategoria(String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }

        CategoriasEntity categoria = new CategoriasEntity();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);

        return categoriasRepository.save(categoria);
    }

    // Listar todas las categorías
    public List<CategoriasEntity> listarCategorias() {
        return categoriasRepository.findAll();
    }

    //DTO 
    public List<CategoriasDTO> listarCategoriasDTO() {
        return categoriasRepository.findAll().stream()
        .map(c -> new CategoriasDTO(
                c.getIdCategoria(),
                c.getNombre()
            ))
            .toList();
    }

    // Buscar categoría por ID
    public CategoriasEntity obtenerPorId(Long id) {
        return categoriasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    }

    // Actualizar una categoría existente
    public CategoriasEntity actualizarCategoria(Long id, String nombre, String descripcion) {
        CategoriasEntity categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        if (nombre != null && !nombre.isBlank()) {
            categoria.setNombre(nombre);
        }
        if (descripcion != null) {
            categoria.setDescripcion(descripcion);
        }

        return categoriasRepository.save(categoria);
    }

    // Eliminar categoría
    public void eliminarCategoria(Long id) {
        if (!categoriasRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoría no encontrada");
        }
        categoriasRepository.deleteById(id);
    }
}