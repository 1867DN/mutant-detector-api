package org.example.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuestas de error estandarizadas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de error")
public class ErrorResponse {

    @Schema(description = "Código de estado HTTP", example = "400")
    private int status;

    @Schema(description = "Mensaje de error", example = "ADN inválido")
    private String message;

    @Schema(description = "Timestamp del error")
    private LocalDateTime timestamp;
}
