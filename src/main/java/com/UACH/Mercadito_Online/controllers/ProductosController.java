package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.DTO.ProductosDTO;
import com.UACH.Mercadito_Online.mappers.ProductosMapper;
import com.UACH.Mercadito_Online.persistance.entities.ProductosEntity;
import com.UACH.Mercadito_Online.services.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @Autowired ProductosMapper productosMapper;

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

    // Obtener productos activos publicados por el usuario
    @GetMapping("/activos/{idUsuario}")
    public ResponseEntity<List<ProductosDTO>> obtenerActivosPorUsuario(@PathVariable Long idUsuario) {
        List<ProductosEntity> productos =productosService.obtenerActivosPorUsuario(idUsuario);

        List<ProductosDTO> productosDTO = productos.stream()
                .map(productosMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productosDTO);
    }

    /*  Obtener productos vendidos por el usuario
    @GetMapping("/vendidos/{idUsuario}")
    public ResponseEntity<List<ProductosEntity>> obtenerVendidosPorUsuario(@PathVariable Long idUsuario) {

        return ResponseEntity.ok(productosService.obtenerVendidosPorUsuario(idUsuario));
    }*/

    // Obtener productos vendidos por el usuario
    @GetMapping("/vendidos/{idUsuario}")
    public ResponseEntity<List<ProductosDTO>> obtenerVendidosPorUsuario(@PathVariable Long idUsuario) {
        List<ProductosEntity> productos = productosService.obtenerVendidosPorUsuario(idUsuario);

        // Convertir entidades a DTOs usando el mapper
        List<ProductosDTO> productosDTO = productos.stream()
                .map(productosMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productosDTO);
    }

}
