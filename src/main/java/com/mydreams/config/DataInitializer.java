package com.mydreams.config;

import com.mydreams.entity.Producto;
import com.mydreams.entity.Usuario;
import com.mydreams.repository.ProductoRepository;
import com.mydreams.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(UsuarioRepository usuarioRepository, 
                          ProductoRepository productoRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Crear usuarios por defecto
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol("ADMIN");
            usuarioRepository.save(admin);
            
            Usuario user = new Usuario();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRol("USER");
            usuarioRepository.save(user);
        }
        
        // Crear productos por defecto
        if (productoRepository.count() == 0) {
            List<Producto> productos = Arrays.asList(
                crearProducto("Kutchen de Manzana", 
                    "Delicioso kutchen casero con manzanas frescas y canela", 
                    new BigDecimal("5500"), 
                    "kutchenDeManzana.jpg", 
                    "Kutchen"),
                crearProducto("Pie de Limón", 
                    "Tarta clásica de limón con merengue italiano", 
                    new BigDecimal("6500"), 
                    "pieDeLimon.jpg", 
                    "Pies"),
                crearProducto("Torta Crema Piña", 
                    "Torta suave con crema y trozos de piña", 
                    new BigDecimal("12000"), 
                    "tortaCremaPina.jpg", 
                    "Tortas"),
                crearProducto("Brownie Chocolate Nuez", 
                    "Brownie húmedo con nueces y chocolate premium", 
                    new BigDecimal("4500"), 
                    "brownieChocolateNuez.jpg", 
                    "Brownies"),
                crearProducto("Cupcakes Variedades", 
                    "Set de 6 cupcakes con diferentes sabores y decoraciones", 
                    new BigDecimal("8000"), 
                    "cupcakesVariedades.jpg", 
                    "Cupcakes"),
                crearProducto("Donas Glaseadas", 
                    "Donas esponjosas con glaseado de colores", 
                    new BigDecimal("3000"), 
                    "donasGlaseadas.jpg", 
                    "Donas"),
                crearProducto("Muffins Plátano Arándano", 
                    "Muffins caseros con plátano y arándanos frescos", 
                    new BigDecimal("3500"), 
                    "muffinsPlatanoArandano.jpg", 
                    "Muffins"),
                crearProducto("Queque Arándano", 
                    "Queque esponjoso con arándanos", 
                    new BigDecimal("6000"), 
                    "quequeArandano.jpg", 
                    "Queques"),
                crearProducto("Queque Mármolado Vainilla", 
                    "Queque con diseño mármol de vainilla y chocolate", 
                    new BigDecimal("6000"), 
                    "quequeMarmoladoVainilla.jpg", 
                    "Queques"),
                crearProducto("Queque Vainilla", 
                    "Queque clásico de vainilla", 
                    new BigDecimal("5500"), 
                    "quequeVainilla.jpg", 
                    "Queques"),
                crearProducto("Rollos de Canela", 
                    "Rollos caseros de canela con glaseado", 
                    new BigDecimal("4000"), 
                    "rollosDeCanela.jpg", 
                    "Rollos"),
                crearProducto("Tartaleta Frutas", 
                    "Tartaleta con frutas frescas de temporada", 
                    new BigDecimal("7000"), 
                    "tartaletaFrutas.jpg", 
                    "Tartaletas"),
                crearProducto("Tarta Yogurt", 
                    "Tarta ligera con yogurt y frutos rojos", 
                    new BigDecimal("7500"), 
                    "tartaYogurth.jpg", 
                    "Tartas"),
                crearProducto("Torta Manjar Lucuma", 
                    "Torta con manjar y lucuma, sabor tradicional", 
                    new BigDecimal("13000"), 
                    "tortaManjarLucuma.jpg", 
                    "Tortas"),
                crearProducto("Torta Manjar Nuez", 
                    "Torta con manjar y nueces tostadas", 
                    new BigDecimal("13000"), 
                    "tortaManjarNuez.jpg", 
                    "Tortas"),
                crearProducto("Torta Merengue Frambuesa", 
                    "Torta con merengue italiano y frambuesas frescas", 
                    new BigDecimal("14000"), 
                    "tortaMerengueFrambueza.jpg", 
                    "Tortas"),
                crearProducto("Torta Selva Negra", 
                    "Torta clásica alemana con chocolate y cerezas", 
                    new BigDecimal("15000"), 
                    "tortaSelvaNegra.jpg", 
                    "Tortas")
            );
            
            productoRepository.saveAll(productos);
        }
    }
    
    private Producto crearProducto(String nombre, String descripcion, BigDecimal precio, 
                                   String imagenUrl, String categoria) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setImagenUrl(imagenUrl);
        producto.setCategoria(categoria);
        producto.setActivo(true);
        return producto;
    }
}
