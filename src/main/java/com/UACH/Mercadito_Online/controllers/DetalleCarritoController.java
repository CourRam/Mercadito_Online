package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoEntity;
import com.UACH.Mercadito_Online.services.DetalleCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-carrito")
@CrossOrigin(origins = "*")
public class DetalleCarritoController {

    @Autowired
    private DetalleCarritoService detalleCarritoService;

    
     //Agregar un producto al carrito.
     
    @PostMapping("/{idCarrito}/agregar")
    public ResponseEntity<DetalleCarritoEntity> agregarProducto(
            @PathVariable Long idCarrito,
            @RequestParam Long idProducto,
            @RequestParam Integer cantidad) {
        DetalleCarritoEntity detalle = detalleCarritoService.agregarProducto(idCarrito, idProducto, cantidad);
        return ResponseEntity.ok(detalle);
    }

    //Listar todos los productos dentro de un carrito específico.
    
    @GetMapping("/{idCarrito}")
    public ResponseEntity<List<DetalleCarritoEntity>> listarPorCarrito(@PathVariable Long idCarrito) {
        List<DetalleCarritoEntity> detalles = detalleCarritoService.listarPorCarrito(idCarrito);
        return ResponseEntity.ok(detalles);
    }

    
    //Actualizar la cantidad de un producto en el carrito.
    
    @PutMapping("/{idCarrito}/actualizar")
    public ResponseEntity<DetalleCarritoEntity> actualizarCantidad(
            @PathVariable Long idCarrito,
            @RequestParam Long idProducto,
            @RequestParam Integer nuevaCantidad) {
        DetalleCarritoEntity actualizado = detalleCarritoService.actualizarCantidad(idCarrito, idProducto, nuevaCantidad);
        return ResponseEntity.ok(actualizado);
    }

    
    //Eliminar un producto específico del carrito.
    
    @DeleteMapping("/{idCarrito}/eliminar")
    public ResponseEntity<String> eliminarProducto(
            @PathVariable Long idCarrito,
            @RequestParam Long idProducto) {
        detalleCarritoService.eliminarProducto(idCarrito, idProducto);
        return ResponseEntity.ok("Producto eliminado del carrito correctamente");
    }

    
    //Vaciar completamente un carrito.
    
    @DeleteMapping("/{idCarrito}/vaciar")
    public ResponseEntity<String> vaciarCarrito(@PathVariable Long idCarrito) {
        detalleCarritoService.vaciarCarrito(idCarrito);
        return ResponseEntity.ok("Carrito vaciado correctamente");
    }
}

