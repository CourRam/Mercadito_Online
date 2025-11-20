package com.UACH.Mercadito_Online.services;

import com.UACH.Mercadito_Online.persistance.entities.CarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.DetalleCarritoID;
import com.UACH.Mercadito_Online.persistance.entities.ProductosEntity;
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
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductosRepository productosRepository;

    // Agregar producto al carrito
    public DetalleCarritoEntity agregarProducto(Long idCarrito, Long idProducto , Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        CarritoEntity carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        ProductosEntity producto = productosRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente para este producto");
        }
        //falta agregar que compruebe stock en funcion del carrito acto activo
        //de modo que reste el stock producto.getStock() la cantidad de ese producto
        //actualmente en el carrito


        DetalleCarritoID id = new DetalleCarritoID(idCarrito, idProducto);
        DetalleCarritoEntity detalle = detalleCarritoRepository.findById(id).orElse(null);

        if (detalle == null) {
            detalle = new DetalleCarritoEntity();
            detalle.setId(id);
            detalle.setCarrito(carrito);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
           
        } else {
            // Si ya existe, actualiza cantidad y subtotal
            detalle.setCantidad(detalle.getCantidad() + cantidad);
           
        }
        detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)));
        return detalleCarritoRepository.save(detalle);
    }

    // Listar productos en un carrito
    public List<DetalleCarritoEntity> listarPorCarrito(Long idCarrito) {
        CarritoEntity carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
        return detalleCarritoRepository.findByCarrito(carrito);
    }

    //  Actualizar cantidad de un producto en el carrito
    public DetalleCarritoEntity actualizarCantidad(Long idCarrito, Long idProducto, Integer nuevaCantidad) {
        if (nuevaCantidad == null || nuevaCantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        DetalleCarritoID id = new DetalleCarritoID(idCarrito, idProducto);
        DetalleCarritoEntity detalle = detalleCarritoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en el carrito"));

        ProductosEntity producto = detalle.getProducto();

        if (producto.getStock() < nuevaCantidad) {
            throw new IllegalArgumentException("Stock insuficiente");
        }

        detalle.setCantidad(nuevaCantidad);
        detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(nuevaCantidad)));

        return detalleCarritoRepository.save(detalle);
    }

    // Eliminar producto del carrito
    public void eliminarProducto(Long idCarrito, Long idProducto) {
        DetalleCarritoID id = new DetalleCarritoID(idCarrito, idProducto);
        if (!detalleCarritoRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado en el carrito");
        }
        detalleCarritoRepository.deleteById(id);
    }

    // Vaciar carrito completo
    public void vaciarCarrito(Long idCarrito) {
        CarritoEntity carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
        List<DetalleCarritoEntity> detalles = detalleCarritoRepository.findByCarrito(carrito);
        detalleCarritoRepository.deleteAll(detalles);
    }
}