package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.DTO.ProductosDTO;
import com.UACH.Mercadito_Online.persistance.entities.ProductosEntity;
import com.UACH.Mercadito_Online.services.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @PostMapping("/crear")
    public ProductosEntity crearProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam("stock") Integer stock,
            @RequestParam("idUsuario") Long idUsuario,
            @RequestParam("idCategoria") Long idCategoria,
            @RequestParam("imagen") MultipartFile imagen) throws IOException {

        return productosService.crearProducto(nombre, descripcion, precio, stock, idUsuario, idCategoria, imagen);
    }

    @GetMapping("/listar")
    public List<ProductosEntity> listarProductos() {
        return productosService.listarProductos();
    }

    @GetMapping("/listarDTO")
    public ResponseEntity<List<ProductosDTO>> listarProductosDTO() {
        return ResponseEntity.ok(productosService.listarProductosDTO());
    }

    @GetMapping("/{id}")
    public ProductosEntity obtenerPorId(@PathVariable Long id) {
        return productosService.obtenerPorId(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productosService.eliminarProducto(id);
    }
}
