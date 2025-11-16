package com.UACH.Mercadito_Online.services;



import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;
import com.UACH.Mercadito_Online.persistance.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private CarritoService carritoService;

    //Crear usuario con todos los argumentos
    public UsuariosEntity crearUsuario(UsuariosEntity usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Verificar si ya existe un usuario con ese correo
        if (usuariosRepository.findAll().stream()
                .anyMatch(u -> u.getCorreo().equalsIgnoreCase(usuario.getCorreo()))) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
        //Guardar Usuario
        UsuariosEntity usuarioGuardado = usuariosRepository.save(usuario);

        //  Crear y guardar el carrito asociado
        carritoService.crearCarrito(usuarioGuardado.getIdUsuario());
        
        return usuarioGuardado;
    }



    // Crear usuario nuevo con datos limitados,Para pruebas
    public UsuariosEntity crearUsuario(String nombre, String email, String password) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Verificar si ya existe un usuario con ese correo
        if (usuariosRepository.findAll().stream()
                .anyMatch(u -> u.getCorreo().equalsIgnoreCase(email))) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
        
        //se crea el usuario
        UsuariosEntity usuario = new UsuariosEntity();
        usuario.setNombre(nombre);
        usuario.setCorreo(email);
        usuario.setPassword(password);
        UsuariosEntity usuarioGuardado = usuariosRepository.save(usuario);

        //  Crear y guardar el carrito asociado
        carritoService.crearCarrito(usuarioGuardado.getIdUsuario());
        
        return usuarioGuardado;
    }

    // Listar todos los usuarios
    public List<UsuariosEntity> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    // Obtener usuario por ID
    public UsuariosEntity obtenerPorId(Long id) {
        return usuariosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    // Eliminar usuario
    public void eliminarUsuario(Long id) {
        if (!usuariosRepository.existsById(id)) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        usuariosRepository.deleteById(id);
    }

    // Buscar usuario por email (útil para login)
    public Optional<UsuariosEntity> buscarPorEmail(String email) {
        return usuariosRepository.findByCorreo(email);
    }

    // Actualizar usuario existente
    public UsuariosEntity actualizarUsuario(Long id, UsuariosEntity nuevosDatos) {
        return usuariosRepository.findById(id).map(usuario -> {
            usuario.setNombre(nuevosDatos.getNombre());
            usuario.setCorreo(nuevosDatos.getCorreo());
            usuario.setTelefono(nuevosDatos.getTelefono());
            usuario.setDireccion(nuevosDatos.getDireccion());
            return usuariosRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
}

