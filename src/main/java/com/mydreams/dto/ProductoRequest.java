package com.mydreams.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar un producto")
public class ProductoRequest {
    
    @NotBlank(message = "El nombre es requerido")
    @Schema(description = "Nombre del producto", example = "Torta de Chocolate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;
    
    @Schema(description = "Descripción del producto", example = "Deliciosa torta de chocolate premium")
    private String descripcion;
    
    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser positivo")
    @Schema(description = "Precio del producto", example = "15000", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal precio;
    
    @Schema(description = "URL de la imagen del producto", example = "tortaChocolate.jpg")
    private String imagenUrl;
    
    @NotBlank(message = "La categoría es requerida")
    @Schema(description = "Categoría del producto", example = "Tortas", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoria;
}
