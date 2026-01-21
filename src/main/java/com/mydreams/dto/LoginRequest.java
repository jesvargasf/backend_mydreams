package com.mydreams.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de autenticación para login")
public class LoginRequest {
    
    @NotBlank(message = "El username es requerido")
    @Schema(description = "Nombre de usuario", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    
    @NotBlank(message = "La contraseña es requerida")
    @Schema(description = "Contraseña del usuario", example = "admin123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
