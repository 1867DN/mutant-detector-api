package org.example.dto;

import org.example.validator.ValidDnaSequence;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para recibir secuencias de ADN en el endpoint POST /mutant.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para análisis de ADN")
public class DnaRequest {

    @NotNull(message = "El ADN no puede ser nulo")
    @NotEmpty(message = "El ADN no puede estar vacío")
    @ValidDnaSequence
    @Schema(description = "Secuencia de ADN representada como array de Strings (matriz NxN)", 
            example = "[\"ATGCGA\", \"CAGTGC\", \"TTATGT\", \"AGAAGG\", \"CCCCTA\", \"TCACTG\"]")
    private String[] dna;
}
