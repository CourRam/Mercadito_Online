package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.DTO.DetalleCarritoDTO;
import com.UACH.Mercadito_Online.mappers.DetalleCarritoMapper;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoEntity;
import com.UACH.Mercadito_Online.services.DetalleCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detalle-carrito")
@CrossOrigin(origins = "*")
public class DetalleCarritoController {

    @Autowired
    private DetalleCarritoService detalleCarritoService;

    @Autowired
    private DetalleCarritoMapper detalleCarritoMapper;

    // Agregar un producto al carrito — devuelve DetalleCarritoDTO
    @PostMapping("/{idCarrito}/agregar")
    public ResponseEntity<?> agregarProducto(
            @PathVariable Long idCarrito,
            @RequestParam Long idProducto) {
        try {
            DetalleCarritoEntity detalleEnt = detalleCarritoService.agregarProducto(idCarrito, idProducto, 1);
            DetalleCarritoDTO dto = detalleCarritoMapper.toDTO(detalleEnt);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar todos los productos dentro de un carrito específico — devuelve lista de DTOs
    @GetMapping("/{idCarrito}")
    public ResponseEntity<List<DetalleCarritoDTO>> listarPorCarrito(@PathVariable Long idCarrito) {
        List<DetalleCarritoEntity> detalles = detalleCarritoService.listarPorCarrito(idCarrito);
        List<DetalleCarritoDTO> dtos = detalles.stream()
                .map(detalleCarritoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Actualizar la cantidad de un producto en el carrito.
    @PutMapping("/{idCarrito}/actualizar")
    public ResponseEntity<?> actualizarCantidad(
            @PathVariable Long idCarrito,
            @RequestParam Long idProducto,
            @RequestParam Integer nuevaCantidad) {
        try {
            DetalleCarritoEntity actualizado = detalleCarritoService.actualizarCantidad(idCarrito, idProducto, nuevaCantidad);
            DetalleCarritoDTO dto = detalleCarritoMapper.toDTO(actualizado);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminar un producto específico del carrito.
    @DeleteMapping("/{idCarrito}/eliminar")
    public ResponseEntity<String> eliminarProducto(
            @PathVariable Long idCarrito,
            @RequestParam Long idProducto) {
        detalleCarritoService.eliminarProducto(idCarrito, idProducto);
        return ResponseEntity.ok("Producto eliminado del carrito correctamente");
    }

    // Vaciar completamente un carrito.
    @DeleteMapping("/{idCarrito}/vaciar")
    public ResponseEntity<String> vaciarCarrito(@PathVariable Long idCarrito) {
        detalleCarritoService.vaciarCarrito(idCarrito);
        return ResponseEntity.ok("Carrito vaciado correctamente");
    }
}
