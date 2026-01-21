package com.mydreams.controller;

import com.mydreams.dto.ProductoRequest;
import com.mydreams.entity.Producto;
import com.mydreams.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
@Tag(name = "Productos", description = "API para gestión de productos (CRUD completo)")
public class ProductoController {
    
    private final ProductoService productoService;
    
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    @Operation(
        summary = "Listar todos los productos",
        description = "Obtiene una lista de todos los productos activos. Este endpoint es público y no requiere autenticación."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))
        )
    })
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(productos);
    }
    
    @Operation(
        summary = "Obtener producto por ID",
        description = "Obtiene los detalles de un producto específico por su ID. Este endpoint es público y no requiere autenticación."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(schema = @Schema(implementation = Producto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(
            @Parameter(description = "ID del producto a obtener", required = true)
            @PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(producto);
    }
    
    @Operation(
        summary = "Crear un nuevo producto",
        description = "Crea un nuevo producto en el sistema. Requiere autenticación JWT."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Producto creado exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error de validación",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado - Token JWT requerido",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Sin permisos para crear productos",
            content = @Content
        )
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoRequest request) {
        Producto producto = productoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }
    
    @Operation(
        summary = "Actualizar un producto existente",
        description = "Actualiza los datos de un producto existente. Requiere autenticación JWT."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error de validación",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado - Token JWT requerido",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Sin permisos para actualizar productos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content
        )
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Producto> actualizar(
            @Parameter(description = "ID del producto a actualizar", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody ProductoRequest request) {
        Producto producto = productoService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(producto);
    }
    
    @Operation(
        summary = "Eliminar un producto",
        description = "Elimina (soft delete) un producto del sistema. Requiere autenticación JWT."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Producto eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado - Token JWT requerido",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Sin permisos para eliminar productos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content
        )
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del producto a eliminar", required = true)
            @PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
