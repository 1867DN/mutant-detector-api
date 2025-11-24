package org.example.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests unitarios para MutantDetector.
 * Cubre todos los casos: mutantes, humanos, validaciones y casos edge.
 */
@DisplayName("MutantDetector - Tests Unitarios")
class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    @SuppressWarnings("unused") // JUnit lo usa automáticamente antes de cada test
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    // ==================== TESTS DE MUTANTES ====================

    @Test
    @DisplayName("Detecta mutante con secuencias horizontales")
    void testMutantWithHorizontalSequences() {
        String[] dna = {
            "AAAATG",
            "TGCAGT",
            "GCTTCC",
            "CCCCTG",
            "GTAGTC",
            "AGTCAC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta mutante con secuencias verticales")
    void testMutantWithVerticalSequences() {
        String[] dna = {
            "AGAATG",
            "GGCAGT",
            "GGTTCC",
            "GGCCTG",
            "GGAGTC",
            "AGTCAC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta mutante con secuencias diagonales descendentes")
    void testMutantWithDiagonalDownSequences() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta mutante con secuencias diagonales ascendentes")
    void testMutantWithDiagonalUpSequences() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATTT",
            "AGAATG",
            "CCCGTA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta mutante con múltiples secuencias mezcladas")
    void testMutantWithMixedSequences() {
        String[] dna = {
            "AAAATG",
            "TGCAGT",
            "TGTTCC",
            "TGCCTG",
            "TGAGTC",
            "AGTCAC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta mutante con exactamente 2 secuencias")
    void testMutantWithExactlyTwoSequences() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    // ==================== TESTS DE HUMANOS ====================

    @Test
    @DisplayName("Detecta humano sin secuencias")
    void testHumanWithNoSequences() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta humano con solo una secuencia")
    void testHumanWithOnlyOneSequence() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATTT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta humano con una secuencia horizontal")
    void testHumanWithOneHorizontalSequence() {
        String[] dna = {
            "AAAATG",
            "TGCAGT",
            "GCATCC",
            "TTCCTG",
            "GTAGTC",
            "AGTCAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta humano con una secuencia vertical")
    void testHumanWithOneVerticalSequence() {
        String[] dna = {
            "ATGCTA",
            "AGGTAC",
            "ATATCT",
            "AGACAG",
            "GCGTCA",
            "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    // ==================== TESTS DE VALIDACIÓN ====================

    @Test
    @DisplayName("Rechaza ADN null")
    void testNullDna() {
        assertFalse(mutantDetector.isMutant(null));
    }

    @Test
    @DisplayName("Rechaza ADN vacío")
    void testEmptyDna() {
        String[] dna = {};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza matriz no cuadrada (filas diferentes)")
    void testNonSquareMatrix() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza matriz con columnas de diferente tamaño")
    void testInvalidMatrixColumnSize() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATG",  // Solo 5 caracteres
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza ADN con caracteres inválidos")
    void testInvalidCharacters() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATXYZ",  // Caracteres inválidos
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza ADN con números")
    void testInvalidCharactersNumbers() {
        String[] dna = {
            "ATGCGA",
            "C12345",  // Números
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza ADN con una fila null")
    void testDnaWithNullRow() {
        String[] dna = {
            "ATGCGA",
            null,
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    // ==================== TESTS DE CASOS EDGE ====================

    @Test
    @DisplayName("Maneja matriz 4x4 (tamaño mínimo) mutante")
    void testMinimumSizeMutant() {
        String[] dna = {
            "AAAA",
            "CCCC",
            "TATA",
            "GAGA"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Maneja matriz 4x4 humano")
    void testMinimumSizeHuman() {
        String[] dna = {
            "ATGC",
            "CGTA",
            "TACG",
            "GCAT"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Maneja matriz grande 10x10 mutante")
    void testLargeMatrixMutant() {
        String[] dna = {
            "ATGCGATTTT",
            "CAGTGCATCG",
            "TTATGTACGA",
            "AGAAGGTTCC",
            "CCCCTAACGT",
            "TCACTGAAAA",
            "ATGCGATTTT",
            "CAGTGCATCG",
            "TTATGTACGA",
            "AGAAGGTTCC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }
}
