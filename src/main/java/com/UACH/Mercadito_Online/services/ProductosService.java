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

        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Precio inválido");
        if (stock == null || stock < 0) throw new IllegalArgumentException("Stock inválido");

        UsuariosEntity usuario = usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        CategoriasEntity categoria = categoriasRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(filePath, imagen.getBytes());

        ProductosEntity producto = new ProductosEntity();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);
        producto.setImagenUrl("/" + filePath.toString().replace("\\", "/"));

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
