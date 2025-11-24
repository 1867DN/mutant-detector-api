package org.example.controller;

import org.example.dto.StatsResponse;
import org.example.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para estadísticas de verificaciones de ADN.
 */
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Tag(name = "Statistics", description = "API para consultar estadísticas de verificaciones de ADN")
public class StatsController {

    private final StatsService statsService;

    /**
     * Endpoint para obtener estadísticas de verificaciones.
     *
     * @return StatsResponse con contadores y ratio
     */
    @GetMapping
    @Operation(
        summary = "Obtener estadísticas",
        description = "Retorna las estadísticas de las verificaciones de ADN: " +
                     "cantidad de mutantes, cantidad de humanos y ratio."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Estadísticas obtenidas exitosamente",
        content = @Content(schema = @Schema(implementation = StatsResponse.class))
    )
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}
