package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.DTO.HistorialVentasDTO;
import com.UACH.Mercadito_Online.persistance.entities.HistorialVentasEntity;
import com.UACH.Mercadito_Online.services.HistorialVentasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/historial-ventas")
@CrossOrigin(origins = "*")
public class HistorialVentasController {

    private final HistorialVentasService historialVentasService;

    public HistorialVentasController(HistorialVentasService historialVentasService) {
        this.historialVentasService = historialVentasService;
    }

    // Obtener todas las ventas
    @GetMapping
    public ResponseEntity<List<HistorialVentasEntity>> listarVentas() {
        return ResponseEntity.ok(historialVentasService.listarVentas());
    }

    // Obtener una venta por ID
    @GetMapping("/{idVenta}")
    public ResponseEntity<HistorialVentasEntity> obtenerPorId(@PathVariable Long idVenta) {
        return ResponseEntity.ok(historialVentasService.buscarPorId(idVenta));
    }

    // Registrar una venta desde un carrito existente
    @PostMapping("/registrar/{idCarrito}")
    public ResponseEntity<HistorialVentasEntity> registrarVenta(@PathVariable Long idCarrito) {
        return ResponseEntity.ok(historialVentasService.registrarVenta(idCarrito));
    }

    //Obtener la ganancia del usuario por ventas
    @GetMapping("/ganancias/{idUsuario}")
    public ResponseEntity<BigDecimal> obtenerGananciasPorVendedor(@PathVariable Long idUsuario) {
        BigDecimal ganancias = historialVentasService.calcularGananciasPorVendedor(idUsuario);
        return ResponseEntity.ok(ganancias);
    }


    // Eliminar una venta
    @DeleteMapping("/{idVenta}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long idVenta) {
        historialVentasService.eliminarVenta(idVenta);
        return ResponseEntity.noContent().build();
    }

    // Obtener historial de compras del usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<HistorialVentasDTO>> obtenerHistorialPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(historialVentasService.obtenerHistorialPorUsuario(idUsuario));
    }
}