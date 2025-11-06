package com.UACH.Mercadito_Online.services;
import com.UACH.Mercadito_Online.persistance.entities.CarritoEntity;
import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;
import com.UACH.Mercadito_Online.persistance.repositories.CarritoRepository;
import com.UACH.Mercadito_Online.persistance.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    /*
        Crear un nuevo carrito para un usuario.
        Si el usuario ya tiene uno activo, se devuelve el existente.
    */
    public CarritoEntity crearCarrito(Long idUsuario) {
        UsuariosEntity usuario = usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Verificar si ya tiene un carrito activo
        List<CarritoEntity> carritos = carritoRepository.findByUsuario(usuario);
        if (!carritos.isEmpty()) {
            return carritos.get(0); // Retorna el primero (único activo)
        }

        CarritoEntity carrito = new CarritoEntity();
        carrito.setUsuario(usuario);
        carrito.setFechaCreacion(new Date());

        return carritoRepository.save(carrito);
    }

    
    // Obtener todos los carritos (solo para administración o debug).
    public List<CarritoEntity> listarCarritos() {
        return carritoRepository.findAll();
    }
    
    
    //Obtener un carrito específico por ID.
    
    public CarritoEntity obtenerPorId(Long idCarrito) {
        return carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
    }

    
    //Eliminar un carrito específico.
    
    public void eliminarCarrito(Long idCarrito) {
        if (!carritoRepository.existsById(idCarrito)) {
            throw new IllegalArgumentException("Carrito no encontrado");
        }
        carritoRepository.deleteById(idCarrito);
    }

    
    //Vaciar todos los carritos (solo para pruebas o administración).

    public void eliminarTodos() {
        carritoRepository.deleteAll();
    }
}