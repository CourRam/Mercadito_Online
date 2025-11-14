package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.persistance.entities.CategoriasEntity;
import com.UACH.Mercadito_Online.services.CategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.UACH.Mercadito_Online.DTO.CategoriasDTO;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriasController {

    @Autowired
    private CategoriasService categoriasService;

    // Crear categoría
    @PostMapping("/crear")
    public CategoriasEntity crearCategoria(
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "descripcion", required = false) String descripcion) {

        return categoriasService.crearCategoria(nombre, descripcion);
    }

    // Listar categorías
    @GetMapping("/listar")
    public List<CategoriasEntity> listarCategorias() {
        return categoriasService.listarCategorias();
    }

    // Listar categorías por DTO
    @GetMapping("/listarDTO")
    public ResponseEntity<List<CategoriasDTO>> listarCategoriasDTO() {
        return ResponseEntity.ok(categoriasService.listarCategoriasDTO());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public CategoriasEntity obtenerPorId(@PathVariable Long id) {
        return categoriasService.obtenerPorId(id);
    }

    // Actualizar categoría
    @PutMapping("/actualizar/{id}")
    public CategoriasEntity actualizarCategoria(
            @PathVariable Long id,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "descripcion", required = false) String descripcion) {

        return categoriasService.actualizarCategoria(id, nombre, descripcion);
    }

    // Eliminar categoría
    @DeleteMapping("/eliminar/{id}")
    public void eliminarCategoria(@PathVariable Long id) {
        categoriasService.eliminarCategoria(id);
    }
}