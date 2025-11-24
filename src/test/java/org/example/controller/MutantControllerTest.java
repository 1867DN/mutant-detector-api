package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DnaRequest;
import org.example.service.MutantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de integración para MutantController.
 */
@WebMvcTest(MutantController.class)
@DisplayName("MutantController - Tests de Integración")
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MutantService mutantService;

    @Test
    @DisplayName("POST /mutant retorna 200 OK para mutante")
    void testPostMutantReturns200ForMutant() throws Exception {
        // Arrange
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        DnaRequest request = new DnaRequest(dna);

        when(mutantService.analyzeDna(any())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant retorna 403 Forbidden para humano")
    void testPostMutantReturns403ForHuman() throws Exception {
        // Arrange
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATTT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
        };
        DnaRequest request = new DnaRequest(dna);

        when(mutantService.analyzeDna(any())).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant retorna 400 Bad Request para ADN null")
    void testPostMutantReturns400ForNullDna() throws Exception {
        // Arrange
        DnaRequest request = new DnaRequest(null);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant retorna 400 Bad Request para ADN vacío")
    void testPostMutantReturns400ForEmptyDna() throws Exception {
        // Arrange
        String[] dna = {};
        DnaRequest request = new DnaRequest(dna);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant retorna 400 Bad Request para matriz no cuadrada")
    void testPostMutantReturns400ForNonSquareMatrix() throws Exception {
        // Arrange
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTAT"  // Fila más corta
        };
        DnaRequest request = new DnaRequest(dna);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant retorna 400 Bad Request para caracteres inválidos")
    void testPostMutantReturns400ForInvalidCharacters() throws Exception {
        // Arrange
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTAXYZ",  // Caracteres inválidos
            "AGACGG",
            "GCGTCA",
            "TCACTG"
        };
        DnaRequest request = new DnaRequest(dna);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant acepta ADN mutante válido con letras mayúsculas")
    void testPostMutantAcceptsValidUppercaseDna() throws Exception {
        // Arrange
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        DnaRequest request = new DnaRequest(dna);

        when(mutantService.analyzeDna(any())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant maneja matriz 4x4 correctamente")
    void testPostMutantHandles4x4Matrix() throws Exception {
        // Arrange
        String[] dna = {
            "AAAA",
            "CCCC",
            "TATA",
            "GAGA"
        };
        DnaRequest request = new DnaRequest(dna);

        when(mutantService.analyzeDna(any())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant maneja request sin Content-Type")
    void testPostMutantHandlesMissingContentType() throws Exception {
        // Arrange
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        DnaRequest request = new DnaRequest(dna);

        // Act & Assert
        // Sin Content-Type, Spring Boot retorna 500 (Internal Server Error)
        // ya que no puede determinar cómo deserializar el request body
        mockMvc.perform(post("/mutant")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
