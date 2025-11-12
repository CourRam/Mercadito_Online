package com.UACH.Mercadito_Online.services;

import com.UACH.Mercadito_Online.persistance.entities.CategoriasEntity;
import com.UACH.Mercadito_Online.persistance.entities.ProductosEntity;
import com.UACH.Mercadito_Online.persistance.entities.UsuariosEntity;
import com.UACH.Mercadito_Online.persistance.repositories.CategoriasRepository;
import com.UACH.Mercadito_Online.persistance.repositories.ProductosRepository;
import com.UACH.Mercadito_Online.persistance.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductosService {
    private static final String UPLOAD_DIR = "uploads/productos/";

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    public ProductosEntity crearProducto(
            String nombre,
            String descripcion,
            BigDecimal precio,
            Integer stock,
            Long idUsuario,
            Long idCategoria,
            MultipartFile imagen) throws IOException {

        // Validaciones básicas
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Precio inválido");
        if (stock == null || stock < 0) throw new IllegalArgumentException("Stock inválido");

        // Validar tipo de archivo
        String contentType = imagen.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten archivos de imagen");
        }

        // Validar tamaño (máximo 5 MB)
        if (imagen.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("La imagen excede el tamaño permitido (5 MB)");
        }

        // Buscar usuario y categoría
        UsuariosEntity usuario = usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        CategoriasEntity categoria = categoriasRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        // Crear carpeta si no existe
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Limpiar nombre de archivo
        String originalName = imagen.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        String fileName = System.currentTimeMillis() + "_" + originalName;
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(filePath, imagen.getBytes());

        // Crear producto
        ProductosEntity producto = new ProductosEntity();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);
        producto.setImagenUrl("/uploads/productos/" + fileName); // URL accesible desde el navegador

        return productosRepository.save(producto);
    }

    public List<ProductosEntity> listarProductos() {
        return productosRepository.findAll();
    }

    public ProductosEntity obtenerPorId(Long id) {
        return productosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    public void eliminarProducto(Long id) {
        productosRepository.deleteById(id);
    }
}