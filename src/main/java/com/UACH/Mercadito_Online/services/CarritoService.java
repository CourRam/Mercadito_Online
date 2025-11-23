package com.UACH.Mercadito_Online.services;
import com.UACH.Mercadito_Online.persistance.entities.CarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;
import com.UACH.Mercadito_Online.persistance.repositories.CarritoRepository;
import com.UACH.Mercadito_Online.persistance.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private HistorialVentasService historialVentasService;

    public CarritoEntity crearCarrito(Long idUsuario) {

        UsuariosEntity usuario = usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        List<CarritoEntity> carritos = carritoRepository
                .findByUsuarioOrderByFechaCreacionDesc(usuario);

        if (!carritos.isEmpty()) {
            CarritoEntity carrito = carritos.get(0);

            if ("EN_PROCESO".equals(carrito.getEstado())) {
                return carrito;
            }
        }

        CarritoEntity nuevo = new CarritoEntity(usuario, new Date());
        return carritoRepository.save(nuevo);
    }


    public CarritoEntity obtenerCarritoActivo(Long idUsuario) {

        UsuariosEntity usuario = usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        List<CarritoEntity> carritos = carritoRepository
                .findByUsuarioOrderByFechaCreacionDesc(usuario);

        if (!carritos.isEmpty()) {
            CarritoEntity carrito = carritos.get(0);

            if ("EN_PROCESO".equals(carrito.getEstado())) {
                return carrito;
            }
        }

        CarritoEntity nuevo = new CarritoEntity(usuario, new Date());
        return carritoRepository.save(nuevo);
    }

    public List<CarritoEntity> listarCarritos() {
        return carritoRepository.findAll();
    }

    public CarritoEntity obtenerPorId(Long idCarrito) {
        return carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
    }

    public void eliminarCarrito(Long idCarrito) {
        if (!carritoRepository.existsById(idCarrito)) {
            throw new IllegalArgumentException("Carrito no encontrado");
        }
        carritoRepository.deleteById(idCarrito);
    }

    public void eliminarTodos() {
        carritoRepository.deleteAll();
    }

    @Transactional
    public void CompletarCompra(Long idCarrito){
        CarritoEntity carrito = obtenerPorId(idCarrito);
        carrito.setEstado("COMPLETADA");
        historialVentasService.registrarVenta(idCarrito);
        crearCarrito(carrito.getUsuario().getIdUsuario());
    }
}