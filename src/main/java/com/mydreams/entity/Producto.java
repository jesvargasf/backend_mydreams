package com.mydreams.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un producto de la pastelería")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "1")
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Nombre del producto", example = "Kutchen de Manzana")
    private String nombre;
    
    @Column(length = 1000)
    @Schema(description = "Descripción detallada del producto", example = "Delicioso kutchen casero con manzanas frescas")
    private String descripcion;
    
    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Precio del producto", example = "5500")
    private BigDecimal precio;
    
    @Column(name = "imagen_url")
    @Schema(description = "URL de la imagen del producto", example = "kutchenDeManzana.jpg")
    private String imagenUrl;
    
    @Column(nullable = false)
    @Schema(description = "Categoría del producto", example = "Kutchen")
    private String categoria;
    
    @Column(nullable = false)
    @Schema(description = "Indica si el producto está activo", example = "true")
    private Boolean activo = true;
}
