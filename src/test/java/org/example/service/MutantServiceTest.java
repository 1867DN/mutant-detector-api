package org.example.service;

import java.util.Optional;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios con mocks para MutantService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MutantService - Tests Unitarios con Mocks")
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    private String[] mutantDna;
    private String[] humanDna;

    @BeforeEach
    @SuppressWarnings("unused") // JUnit lo usa automáticamente antes de cada test
    void setUp() {
        mutantDna = new String[]{
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };

        humanDna = new String[]{
            "ATGCGA",
            "CAGTGC",
            "TTATTT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
        };
    }

    @Test
    @DisplayName("Analiza ADN mutante nuevo y lo persiste")
    void testAnalyzeMutantDnaAndPersist() {
        // Arrange
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(mutantDna)).thenReturn(true);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());

        // Act
        boolean result = mutantService.analyzeDna(mutantDna);

        // Assert
        assertTrue(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, times(1)).isMutant(mutantDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Analiza ADN humano nuevo y lo persiste")
    void testAnalyzeHumanDnaAndPersist() {
        // Arrange
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(humanDna)).thenReturn(false);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());

        // Act
        boolean result = mutantService.analyzeDna(humanDna);

        // Assert
        assertFalse(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, times(1)).isMutant(humanDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Retorna resultado existente sin re-analizar (deduplicación)")
    void testReturnExistingResultWithoutReanalysis() {
        // Arrange
        DnaRecord existingRecord = new DnaRecord();
        existingRecord.setMutant(true);

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord));

        // Act
        boolean result = mutantService.analyzeDna(mutantDna);

        // Assert
        assertTrue(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }
}
