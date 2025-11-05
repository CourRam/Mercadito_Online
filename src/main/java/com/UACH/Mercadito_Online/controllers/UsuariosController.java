package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;
import com.UACH.Mercadito_Online.services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    // Crear usuario nuevo
    @PostMapping("/crear")
    public UsuariosEntity crearUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        return usuariosService.crearUsuario(nombre, email, password);
    }

    // Listar usuarios
    @GetMapping("/listar")
    public List<UsuariosEntity> listarUsuarios() {
        return usuariosService.listarUsuarios();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public UsuariosEntity obtenerPorId(@PathVariable Long id) {
        return usuariosService.obtenerPorId(id);
    }

    // Eliminar usuario
    @DeleteMapping("/eliminar/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuariosService.eliminarUsuario(id);
    }

     // Actualizar usuario existente
    @PutMapping("/{id}")
    public UsuariosEntity actualizarUsuario(@PathVariable Long id, @RequestBody UsuariosEntity nuevosDatos) {
        return usuariosService.actualizarUsuario(id, nuevosDatos);
    }

    // Buscar usuario por email (útil para login)
    @GetMapping("/buscar")
    public Optional<UsuariosEntity> buscarPorEmail(@RequestParam String email) {
        return usuariosService.buscarPorEmail(email);
    }

}