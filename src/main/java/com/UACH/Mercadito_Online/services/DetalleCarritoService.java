package com.UACH.Mercadito_Online.services;

import com.UACH.Mercadito_Online.persistance.entities.*;
import com.UACH.Mercadito_Online.persistance.repositories.CarritoRepository;
import com.UACH.Mercadito_Online.persistance.repositories.DetalleCarritoRepository;
import com.UACH.Mercadito_Online.persistance.repositories.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DetalleCarritoService {

    @Autowired
    private DetalleCarritoRepository detalleCarritoRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private CarritoRepository carritoRepository;


    // =============================
    //   AGREGAR PRODUCTO AL CARRITO
    // =============================
    public DetalleCarritoEntity agregarProducto(Long idCarrito, Long idProducto, Integer cantidadAgregar) {

        ProductosEntity producto = productosRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CarritoEntity carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Buscar si ya existe con el repository correcto
        DetalleCarritoEntity existente = detalleCarritoRepository
                .findByCarritoAndProducto(carrito, producto)
                .orElse(null);

        int cantidadActual = (existente != null) ? existente.getCantidad() : 0;

        // Validación de stock
        if (cantidadActual + cantidadAgregar > producto.getStock()) {
            throw new RuntimeException("No hay suficiente stock disponible");
        }

        if (existente != null) {
            existente.setCantidad(cantidadActual + cantidadAgregar);
            existente.setSubtotal(
                    producto.getPrecio().multiply(new BigDecimal(existente.getCantidad()))
            );
            return detalleCarritoRepository.save(existente);
        }

        // Si no existe, crear nuevo detalle
        DetalleCarritoEntity nuevo = new DetalleCarritoEntity();

        // ID compuesto sincronizado

        nuevo.setCarrito(carrito);
        nuevo.setProducto(producto);
        nuevo.setCantidad(cantidadAgregar);
        nuevo.setSubtotal(
                producto.getPrecio().multiply(new BigDecimal(cantidadAgregar))
        );

        return detalleCarritoRepository.save(nuevo);
    }


    // =============================
    //      LISTAR DETALLES
    // =============================
    public List<DetalleCarritoEntity> listarPorCarrito(Long idCarrito) {
        return detalleCarritoRepository.findByCarrito_IdCarrito(idCarrito);
    }


    // =============================
    //      ACTUALIZAR CANTIDAD
    // =============================
    public DetalleCarritoEntity actualizarCantidad(Long idCarrito, Long idProducto, Integer nuevaCantidad) {

        ProductosEntity producto = productosRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (nuevaCantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        if (nuevaCantidad > producto.getStock()) {
            throw new RuntimeException("La cantidad excede el stock disponible");
        }

        CarritoEntity carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        DetalleCarritoEntity detalle = detalleCarritoRepository
                .findByCarritoAndProducto(carrito, producto)
                .orElseThrow(() -> new RuntimeException("El producto no está en el carrito"));

        detalle.setCantidad(nuevaCantidad);
        detalle.setSubtotal(
                producto.getPrecio().multiply(new BigDecimal(nuevaCantidad))
        );

        return detalleCarritoRepository.save(detalle);
    }


    // =============================
    //      ELIMINAR PRODUCTO
    // =============================
    public void eliminarProducto(Long idCarrito, Long idProducto) {
        DetalleCarritoID id = new DetalleCarritoID(idCarrito, idProducto);
        detalleCarritoRepository.deleteById(id);
    }


    // =============================
    //      VACIAR CARRITO
    // =============================
    public void vaciarCarrito(Long idCarrito) {
        List<DetalleCarritoEntity> detalles = listarPorCarrito(idCarrito);
        detalleCarritoRepository.deleteAll(detalles);
    }
}
