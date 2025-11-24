package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios con mocks para StatsService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("StatsService - Tests Unitarios con Mocks")
class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("Obtiene estadísticas con mutantes y humanos")
    void testGetStatsWithMutantsAndHumans() {
        // Arrange
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertNotNull(stats);
        assertEquals(40L, stats.getCountMutantDna());
        assertEquals(100L, stats.getCountHumanDna());
        assertEquals(0.4, stats.getRatio(), 0.001);
        verify(dnaRecordRepository, times(1)).countByIsMutant(true);
        verify(dnaRecordRepository, times(1)).countByIsMutant(false);
    }

    @Test
    @DisplayName("Obtiene estadísticas sin humanos (ratio 0)")
    void testGetStatsWithNoHumans() {
        // Arrange
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(5L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertNotNull(stats);
        assertEquals(5L, stats.getCountMutantDna());
        assertEquals(0L, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    @DisplayName("Obtiene estadísticas sin registros")
    void testGetStatsWithNoRecords() {
        // Arrange
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertNotNull(stats);
        assertEquals(0L, stats.getCountMutantDna());
        assertEquals(0L, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    @DisplayName("Calcula ratio correctamente con diferentes proporciones")
    void testCalculateRatioCorrectly() {
        // Test 1:1 ratio
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(50L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(50L);

        StatsResponse stats = statsService.getStats();
        assertEquals(1.0, stats.getRatio(), 0.001);
    }

    @Test
    @DisplayName("Obtiene estadísticas solo con mutantes")
    void testGetStatsOnlyMutants() {
        // Arrange
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(10L, stats.getCountMutantDna());
        assertEquals(0L, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    @DisplayName("Obtiene estadísticas solo con humanos")
    void testGetStatsOnlyHumans() {
        // Arrange
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(20L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(0L, stats.getCountMutantDna());
        assertEquals(20L, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }
}
