package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.DnaRequest;
import org.example.dto.ErrorResponse;
import org.example.service.MutantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para detección de mutantes.
 */
@RestController
@RequestMapping("/mutant")
@RequiredArgsConstructor
@Tag(name = "Mutant Detection", description = "API para detección de mutantes mediante análisis de ADN")
public class MutantController {

    private final MutantService mutantService;

    /**
     * Endpoint para verificar si un ADN corresponde a un mutante.
     *
     * @param request DnaRequest con la secuencia de ADN
     * @return 200 OK si es mutante, 403 Forbidden si es humano
     */
    @PostMapping
    @Operation(
        summary = "Detectar mutante",
        description = "Analiza una secuencia de ADN y determina si corresponde a un mutante. " +
                    "Un humano es mutante si se encuentran más de una secuencia de 4 letras iguales " +
                    "(horizontal, vertical o diagonal)."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Es mutante - ADN válido con más de una secuencia detectada",
            content = @Content(schema = @Schema(implementation = Void.class))
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No es mutante - ADN humano normal",
            content = @Content(schema = @Schema(implementation = Void.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "ADN inválido - Matriz no cuadrada, caracteres inválidos o null",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyzeDna(request.getDna());

        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
