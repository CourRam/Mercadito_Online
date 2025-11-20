package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;
import com.UACH.Mercadito_Online.services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.UACH.Mercadito_Online.DTO.UsuariosDTO;

import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    
    // Crear usuario nuevo con todos los datos
    @PostMapping("/crearFull")
    public UsuariosEntity crearUsuarioFull(@RequestBody UsuariosDTO dto) {
        UsuariosEntity usuario=new UsuariosEntity();  
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());  
        return usuariosService.crearUsuario(usuario);
    }

    // Crear usuario nuevo sin todos los datos
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

    // Iniciar sesion
     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String correo = body.get("correo");
        String password = body.get("password");

        /* 
        Optional<UsuariosEntity> usuariosEntityOptional =usuariosService.buscarPorEmail(correo);
        UsuariosEntity u=usuariosEntityOptional.get();
        */
        Optional<UsuariosEntity> opt = usuariosService.buscarPorEmail(correo);
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        UsuariosEntity u = opt.get();

        if (!u.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
        
        if (u == null || !u.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        return ResponseEntity.ok(Map.of(
                "idUsuario", u.getIdUsuario(),
                "nombre", u.getNombre(),
                "correo", u.getCorreo()
        ));
    }


    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody UsuariosEntity nuevo) {
        
        if (usuariosService.buscarPorEmail(nuevo.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("Correo ya registrado");
        }
        
        usuariosService.crearUsuario(nuevo);

        return ResponseEntity.ok("Usuario registrado");
    }
}