package org.example.controller;

import org.example.dto.StatsResponse;
import org.example.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para StatsController.
 */
@WebMvcTest(StatsController.class)
@DisplayName("StatsController - Tests de Integración")
class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    @Test
    @DisplayName("GET /stats retorna 200 OK con estadísticas")
    void testGetStatsReturns200WithStats() throws Exception {
        // Arrange
        StatsResponse stats = new StatsResponse(40L, 100L, 0.4);
        when(statsService.getStats()).thenReturn(stats);

        // Act & Assert
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    @Test
    @DisplayName("GET /stats retorna estadísticas con ratio 0 cuando no hay humanos")
    void testGetStatsReturnsZeroRatioWithNoHumans() throws Exception {
        // Arrange
        StatsResponse stats = new StatsResponse(5L, 0L, 0.0);
        when(statsService.getStats()).thenReturn(stats);

        // Act & Assert
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(5))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));
    }

    @Test
    @DisplayName("GET /stats retorna estadísticas vacías cuando no hay registros")
    void testGetStatsReturnsEmptyStats() throws Exception {
        // Arrange
        StatsResponse stats = new StatsResponse(0L, 0L, 0.0);
        when(statsService.getStats()).thenReturn(stats);

        // Act & Assert
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));
    }

    @Test
    @DisplayName("GET /stats retorna Content-Type JSON")
    void testGetStatsReturnsJsonContentType() throws Exception {
        // Arrange
        StatsResponse stats = new StatsResponse(40L, 100L, 0.4);
        when(statsService.getStats()).thenReturn(stats);

        // Act & Assert
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}
