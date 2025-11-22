package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.DTO.CarritoDTO;
import com.UACH.Mercadito_Online.mappers.CarritoMapper;
import com.UACH.Mercadito_Online.persistance.entities.CarritoEntity;
import com.UACH.Mercadito_Online.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@CrossOrigin(origins = "*")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Crear un nuevo carrito para un usuario.
    @PostMapping("/crear")
    public ResponseEntity<CarritoDTO> crearCarrito(@RequestParam Long idUsuario) {
        CarritoEntity carrito = carritoService.crearCarrito(idUsuario);
        return ResponseEntity.ok(CarritoMapper.toDTO(carrito));
    }

    // Obtener carrito actual (ahora devuelve DTO con detalles y total)
    @PostMapping("/carrito-actual")
    public ResponseEntity<CarritoDTO> obtenerCarritoActivo(@RequestParam Long idUsuario) {
        CarritoEntity carrito = carritoService.obtenerCarritoActivo(idUsuario);
        return ResponseEntity.ok(CarritoMapper.toDTO(carrito));
    }

    // Obtener todos los carritos (admin/debug) - devuelve entidades (opcional)
    @GetMapping
    public ResponseEntity<List<CarritoEntity>> listarCarritos() {
        List<CarritoEntity> carritos = carritoService.listarCarritos();
        return ResponseEntity.ok(carritos);
    }

    // Obtener un carrito por su ID (devuelve DTO para consistencia)
    @GetMapping("/{idCarrito}")
    public ResponseEntity<CarritoDTO> obtenerPorId(@PathVariable Long idCarrito) {
        CarritoEntity carrito = carritoService.obtenerPorId(idCarrito);
        return ResponseEntity.ok(CarritoMapper.toDTO(carrito));
    }

    // Eliminar un carrito por ID.
    @DeleteMapping("/{idCarrito}")
    public ResponseEntity<String> eliminarCarrito(@PathVariable Long idCarrito) {
        carritoService.eliminarCarrito(idCarrito);
        return ResponseEntity.ok("Carrito eliminado correctamente");
    }

    // Eliminar todos los carritos (solo para pruebas o administración).
    @DeleteMapping("/eliminar-todos")
    public ResponseEntity<String> eliminarTodos() {
        carritoService.eliminarTodos();
        return ResponseEntity.ok("Todos los carritos eliminados correctamente");
    }
}
