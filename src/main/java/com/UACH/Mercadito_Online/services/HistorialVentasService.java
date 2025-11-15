package com.UACH.Mercadito_Online.services;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UACH.Mercadito_Online.persistance.entities.CarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.HistorialVentasEntity;
import com.UACH.Mercadito_Online.persistance.repositories.CarritoRepository;
import com.UACH.Mercadito_Online.persistance.repositories.DetalleCarritoRepository;
import com.UACH.Mercadito_Online.persistance.repositories.HistorialVentasRepository;
import com.UACH.Mercadito_Online.persistance.repositories.ProductosRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//@AllArgsConstructor
//@NoArgsConstructor
@Service
public class HistorialVentasService {

    @Autowired
    private  HistorialVentasRepository historialVentasRepository;
    @Autowired
    private  CarritoRepository carritoRepository;
    @Autowired
    private  DetalleCarritoRepository detalleCarritoRepository;
    @Autowired
    private  ProductosRepository productosRepository;


    // Listar todas las ventas
    public List<HistorialVentasEntity> listarVentas() {
        return historialVentasRepository.findAll();
    }



    // Buscar una venta por ID
    public HistorialVentasEntity buscarPorId(Long idVenta) {
        return historialVentasRepository.findById(idVenta)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada con ID: " + idVenta));
    }


    // Registrar una nueva venta desde un carrito
    @Transactional
    public HistorialVentasEntity registrarVenta(Long idCarrito) {
        CarritoEntity carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        List<DetalleCarritoEntity> detalles = detalleCarritoRepository.findByCarrito_IdCarrito(idCarrito);
        if (detalles.isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío, no se puede registrar una venta.");
        }

        // Calcular total
        BigDecimal total = detalles.stream()
                .map(DetalleCarritoEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Crear venta
        HistorialVentasEntity venta = new HistorialVentasEntity();
        venta.setCarrito(carrito);
        venta.setFecha(LocalDateTime.now());
        venta.setUsuario(carrito.getUsuario());
        venta.setTotal(total);
        //venta.setEstado("COMPLETADA");

        // Guardar venta
        HistorialVentasEntity ventaGuardada = historialVentasRepository.save(venta);

        // Reducir stock de productos vendidos
        for (DetalleCarritoEntity detalle : detalles) {
            var producto = detalle.getProducto();
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productosRepository.save(producto);
        }

        // Vaciar carrito-- esta mal... maybe
        //detalleCarritoRepository.deleteAll(detalles);

        return ventaGuardada;
    }
    
    //Calcular ganancias por usuario
    public BigDecimal calcularGananciasPorVendedor(Long idUsuario) {
        return historialVentasRepository.calcularGananciasPorVendedor(idUsuario);
    }

    // Eliminar una venta (opcional)
    public void eliminarVenta(Long idVenta) {
        if (!historialVentasRepository.existsById(idVenta)) {
            throw new IllegalArgumentException("No se encontró la venta con ID: " + idVenta);
        }
        historialVentasRepository.deleteById(idVenta);
    }
}